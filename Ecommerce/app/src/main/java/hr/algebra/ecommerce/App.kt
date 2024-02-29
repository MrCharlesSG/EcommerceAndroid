package hr.algebra.ecommerce

import android.app.Activity
import android.app.Application
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.product.ProductDao
import hr.algebra.ecommerce.services.CartAS
import hr.algebra.ecommerce.services.CategorySA
import hr.algebra.ecommerce.services.MyListAS
import hr.algebra.ecommerce.services.NavigationAS
import hr.algebra.ecommerce.services.ProductAS
import hr.algebra.ecommerce.services.PurchasesAS
import hr.algebra.ecommerce.ui.NotAuthenticatedDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class App : Application(), ActivityHost {
    private lateinit var productDao: ProductDao

    private lateinit var asNavigation : NavigationAS
    private lateinit var asCart : CartAS
    private lateinit var asMyList : MyListAS
    private lateinit var asProduct : ProductAS
    private lateinit var asPurchase : PurchasesAS
    private lateinit var asCategory: CategorySA

    private lateinit var activity: Activity

    override fun onCreate() {
        super.onCreate()

        productDao = AppDatabase.getInstance(this)
            .productDao()
        asProduct = ProductAS(applicationContext)
        asCategory = CategorySA(applicationContext)
        asCart = CartAS(applicationContext)
        asMyList = MyListAS(applicationContext)
        asNavigation = NavigationAS(applicationContext)
        asPurchase = PurchasesAS(applicationContext)
    }

    fun getProductDao() = productDao
    fun getNavigationAS() = asNavigation
    fun getProductAS() = asProduct
    fun getCartAS() = asCart
    fun getMyListAS() = asMyList
    fun getCategoryAS() =  asCategory

    fun getPurchaseAS() = asPurchase
    override fun setActivity(activity: Activity) {
        this.activity = activity
    }

    override fun showNotAuthenticatedDialog() {
        NotAuthenticatedDialog(activity).show()
    }

    override suspend fun showNotAuthenticatedDialogSuspend() {
        withContext(Dispatchers.Main) {
            showNotAuthenticatedDialog()
        }
    }
}