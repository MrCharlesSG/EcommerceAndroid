package hr.algebra.ecommerce.services

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.mylist.MyListDao
import hr.algebra.ecommerce.model.ProductEcommerce
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val MY_LIST_REFERENCE = "myList"

class MyListAS(context: Context) : ApplicationAuthenticatedService(context) {

    private val myListDao: MyListDao = AppDatabase.getInstance(context)
        .myListDao()
    private val saProduct: ProductAS = (context.applicationContext as App).getProductAS()

    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }


    suspend fun insert(product: ProductEcommerce, updateInterface: () -> Unit) {
        authenticateFunction {
            val productListRef = ref.child(user!!).child(MY_LIST_REFERENCE)
            productListRef.child(product.id.toString()).setValue(product.id)
            updateInterface()
        }
    }

    suspend fun delete(product: ProductEcommerce, updateInterface: () -> Unit) {
        authenticateFunction {
            val productListRef = ref.child(user!!).child(MY_LIST_REFERENCE)
            productListRef.child(product.id.toString()).removeValue()

            updateInterface()
        }
    }

    suspend fun getMyList(): MutableList<ProductEcommerce> {
        val productList = mutableListOf<ProductEcommerce>()
        val deferred = CompletableDeferred<Unit>()

        authenticateFunction {
            val productListRef = ref.child(user!!).child(MY_LIST_REFERENCE)


            productListRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    GlobalScope.launch(Dispatchers.IO) {
                        snapshot.children.forEach { element ->
                            val productId = element.getValue(Int::class.java)
                            if (productId != null) {
                                val product = saProduct.getProduct(productId)
                                if (product != null) productList.add(product)
                            }
                        }
                        deferred.complete(Unit)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Canceled", error.toString())
                    deferred.completeExceptionally(error.toException())
                }
            })
        }


        deferred.await()
        return productList
    }

    fun isProductInMyList(id: Int, callback: (Boolean) -> Unit) {
        var isInMyList: Boolean
        if (isAuthenticated()) {
            val productListRef = ref.child(user!!).child(MY_LIST_REFERENCE)
            productListRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isInMyList = snapshot.hasChild(id.toString())
                    callback(isInMyList)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(false)
                }
            })
        } else {
            callback(false)
        }
    }


}