package hr.algebra.ecommerce.services

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.mylist.MyListDao
import hr.algebra.ecommerce.dal.mylist.MyListEntity
import hr.algebra.ecommerce.model.ProductEcommerce

class MyListAS(context: Context) : ApplicationAuthenticatedService(context) {

    private val myListDao: MyListDao = AppDatabase.getInstance(context)
        .myListDao()
    private val saProduct : ProductAS = (context.applicationContext as App).getProductAS()

    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }
    suspend fun insert(product: ProductEcommerce, updateInterface: () -> Unit) {
        authenticateFunction{
            /*myListDao.insert(
                MyListEntity(
                    product.id
                )
            )
             */
            /*val currentUser = AuthManagerRepository.INSTANCE.getAuthManager().getUserLogged()
            currentUser?.uid?.let { userId ->
                /*val userMyListReference =
                    databaseReference.child("users").child(userId).child("myList")
                userMyListReference.setValue(MyListEntity(
                    product.id
                )).addOnSuccessListener {
                    Log.e("EN firebase", "Success")
                }.addOnFailureListener {
                    Log.e("EN firebase", "Failure")
                }.addOnCanceledListener {
                    Log.e("EN firebase", "Cancel")
                }.addOnCompleteListener {
                    Log.e("EN firebase", "Complete")
                }
                 */
                val myList = product.id
                databaseReference.child("users").child(userId).setValue(myList)
            }
             */
            updateInterface()
        }
    }

    suspend fun delete(product: ProductEcommerce, updateInterface: () -> Unit) {
        authenticateFunction{
            myListDao.delete(
                MyListEntity(
                    product.id
                )
            )
            updateInterface()
        }
    }

    fun getMyList(): MutableList<ProductEcommerce> {
        val myListProducts = myListDao.getAll()
        val productEcommerceList = mutableListOf<ProductEcommerce>()

        myListProducts.forEach{myListProduct ->
            val product = saProduct.getProduct(myListProduct._id)
            if(product!=null){
                product.setInMyList(true)
                productEcommerceList.add(product)
            }
            /*val currentUser = AuthManagerRepository.INSTANCE.getAuthManager().getUserLogged()
            currentUser?.uid?.let { userId ->
                val myListReference = databaseReference.child("usuarios").child(userId).child("myList")
                myListReference.addValueEventListener(listener)
            }*/
        }

        return productEcommerceList
    }

    fun isProductInMyList(id: Int) : Boolean = myListDao.get(id)!=null

}