package hr.algebra.ecommerce.services

import android.content.Context
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.product.ProductDao
import hr.algebra.ecommerce.model.ProductEcommerce

class ProductAS(private val context: Context) {

    private val productDao: ProductDao = AppDatabase.getInstance(context)
        .productDao()

    fun getProduct(_id: Int): ProductEcommerce? {
        val entity = productDao.getProduct(_id)
        return if (entity != null) ProductEcommerce.getFromEntity(entity)
        else null
    }

    fun getAllProducts() : List<ProductEcommerce> {
        val productEntities = productDao.getAllProducts()
        val productEcommerceList = mutableListOf<ProductEcommerce>()
        productEntities.forEach { productEntity ->
            val productEcommerce = ProductEcommerce.getFromEntity(productEntity)

            productEcommerce.setInMyList(
                (context.applicationContext as App).getMyListAS().isProductInMyList(productEcommerce.id)
            )
            productEcommerceList.add(productEcommerce)
        }
        return productEcommerceList
    }


}