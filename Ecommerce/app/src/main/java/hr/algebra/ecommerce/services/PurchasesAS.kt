package hr.algebra.ecommerce.services

import android.content.Context
import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.handler.downloadAndStoreFromFirebase
import hr.algebra.ecommerce.model.CartElementEcommerce
import hr.algebra.ecommerce.model.Purchase
import java.io.File
import java.time.LocalDate

private const val PURCHASE_REF = "purchases"

class PurchasesAS(private val context: Context) : ApplicationAuthenticatedService(context) {
    private val saCart = (context.applicationContext as App).getCartAS()

    fun getAllPurchases(callBack : (List<Purchase>) -> Unit ) {
            val purchasesRef = ref.child(user!!).child(PURCHASE_REF)

            purchasesRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Purchase>()
                    snapshot.children.forEach { element ->
                        val purchase = element.getValue(Purchase::class.java)
                        purchase?.let {
                            it.products.forEach {element->
                                downloadImageFromFirebase(element)
                            }
                            list.add(it)
                        }
                    }
                    callBack(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


    }

    fun getAllPurchasesAndTotal( callBack : (Pair<Int, List<Purchase>>) -> Unit ) {
        val purchasesRef = ref.child(user!!).child(PURCHASE_REF)
        purchasesRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Purchase>()
                var totalExpended = 0
                snapshot.children.forEach { element ->
                    val purchase = element.getValue(Purchase::class.java)
                    purchase?.let {
                        totalExpended += it.price
                        list.add(it)
                    }
                }
                callBack(Pair(totalExpended, list))
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    suspend fun buy(): Boolean =
        authenticateFunction {
            val cart = saCart.getCart()
            val purchaseID = LocalDate.now().toEpochDay()

            val purchaseRef = ref.child(user!!).child(PURCHASE_REF).child(purchaseID.toString())

            purchaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listToAdd = MutableList(cart.getList().size) {false}
                    var purchase : Purchase?
                    val cartList = cart.getList()
                    if (snapshot.exists()) {
                        purchase = snapshot.getValue(Purchase::class.java)
                        purchase?.let { purchase ->
                            purchase.price += cart.getTotalPrice()
                            purchase.products?.let {
                                purchase.products.forEach { cartDB ->
                                    cartList.forEachIndexed { index, cartNew ->
                                        if (cartDB.product.id == cartNew.product.id) {
                                            cartDB.quantity += cartNew.quantity
                                            listToAdd[index] = true
                                        }
                                    }
                                }
                            }
                            cartList.forEachIndexed { index, cartElementEcommerce ->
                                if(!listToAdd[index]) {
                                    uploadImageToFirebase(cartElementEcommerce)
                                    purchase.products.add(cartElementEcommerce)
                                }
                            }
                        }
                    }else{
                        cartList.forEach {
                            uploadImageToFirebase(it)
                        }
                        purchase = Purchase(
                            purchaseID,
                            cart.getTotalPrice(),
                            cartList
                        )
                    }
                    purchaseRef.setValue(purchase)
                    saCart.clearCart()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }


    fun uploadImageToFirebase(cartElementEcommerce: CartElementEcommerce){
        val images = mutableListOf<String>()
        cartElementEcommerce.product.images.forEach {
            val storageRef = FirebaseStorage.getInstance().reference
            val image = File(it)
            val file = Uri.fromFile(File(it))

            val imageRef = storageRef.child("images/${image.name}")
            imageRef.putFile(file)
            images.add(image.name)
        }
        cartElementEcommerce.product.images=images
    }

    fun downloadImageFromFirebase(cartElementEcommerce: CartElementEcommerce){
        val images = mutableListOf<String>()
        cartElementEcommerce.product.images.forEach{
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/${it}")
            downloadAndStoreFromFirebase(context, imageRef){ newPath ->
                if (newPath != null) {
                    images.add(newPath)
                }
            }
        }
        cartElementEcommerce.product.images = images
    }

}