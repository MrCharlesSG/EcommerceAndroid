package hr.algebra.ecommerce

import android.app.Activity
import android.app.Application
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.category.CategoryDao
import hr.algebra.ecommerce.dal.mylist.MyListDao
import hr.algebra.ecommerce.dal.product.ProductDao
import hr.algebra.ecommerce.dal.purchase.PurchaseDao
import hr.algebra.ecommerce.services.CartAS
import hr.algebra.ecommerce.services.NavigationAS
import hr.algebra.ecommerce.ui.NotAuthenticatedDialog

class App : Application() {
    private lateinit var productDao: ProductDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var purchaseDao: PurchaseDao
    private lateinit var myListDao: MyListDao
    private lateinit var asNavigation : NavigationAS
    private lateinit var asCart : CartAS
    private lateinit var activity: Activity


    override fun onCreate() {
        super.onCreate()

        productDao = AppDatabase.getInstance(this)
            .productDao()
        categoryDao = AppDatabase.getInstance(this)
            .categoryDao()
        purchaseDao = AppDatabase.getInstance(this)
            .purchaseDao()
        myListDao = AppDatabase.getInstance(this)
            .myListDao()
        asCart = CartAS(applicationContext)
        asNavigation = NavigationAS(applicationContext)
    }

    fun getProductDao() = productDao
    fun getCategoryDao() = categoryDao
    fun getPurchasedDao() = purchaseDao
    fun getMyListDao() = myListDao
    fun getNavigationAS() = asNavigation

    fun getCart() = asCart.getCart()

    fun getCartAS() = asCart
    fun setActivity(activity: Activity) {
        this.activity = activity
    }

    fun showNotAuthenticatedDialog() {
        NotAuthenticatedDialog(activity).show()
    }
}