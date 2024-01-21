package hr.algebra.ecommerce

import android.app.Application
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.cart.CartDao
import hr.algebra.ecommerce.dal.cart.CartEntity
import hr.algebra.ecommerce.dal.category.CategoryDao
import hr.algebra.ecommerce.dal.mylist.MyListDao
import hr.algebra.ecommerce.dal.product.ProductDao
import hr.algebra.ecommerce.dal.purchase.PurchaseDao
import hr.algebra.ecommerce.model.Cart
import hr.algebra.ecommerce.model.CartElementEcommerce
import hr.algebra.ecommerce.model.ProductEcommerce

class App : Application() {
    private lateinit var productDao: ProductDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var cartDao: CartDao
    private lateinit var cart: Cart
    private lateinit var purchaseDao: PurchaseDao
    private lateinit var myListDao: MyListDao


    override fun onCreate() {
        super.onCreate()
        productDao = AppDatabase.getInstance(this)
            .productDao()
        categoryDao = AppDatabase.getInstance(this)
            .categoryDao()
        cartDao = AppDatabase.getInstance(this)
            .cartDao()
        purchaseDao = AppDatabase.getInstance(this)
            .purchaseDao()
        myListDao = AppDatabase.getInstance(this)
            .myListDao()
        cart = Cart(0, applicationContext, mutableListOf())
    }

    fun getProductDao() = productDao
    fun getCategoryDao() = categoryDao
    fun getCartDao() = cartDao
    fun getPurchasedDao() = purchaseDao
    fun getMyListDao() = myListDao

    fun getCart() = cart

    fun addToCart(cartEntity: CartEntity, product: ProductEcommerce) {
        cart.addToCart(
            CartElementEcommerce(
                cartEntity._id,
                cartEntity.quantity,
                product
            )
        )
    }

    fun removeFromCart(cartEntity: CartEntity, product: ProductEcommerce) {
        cart.remove(CartElementEcommerce(
            cartEntity._id,
            cartEntity.quantity,
            product
        ))

    }

    fun clearCart() {
        cart.clear()
    }

    fun removeFromCart(cartElement: CartElementEcommerce) {
        cart.remove(cartElement)
    }

    fun updateCartMyList(product: ProductEcommerce) {
        cart.getList().forEach {
            if(it._id== product.id){
                it.product.setInMyList(product.isInMyList())
            }
        }

    }
}