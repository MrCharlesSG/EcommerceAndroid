package hr.algebra.ecommerce.api

import android.content.Context
import android.util.Log
import hr.algebra.ecomerce2.framework.sendBroadcast
import hr.algebra.ecommerce.EcommerceReceiver
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.category.CategoryEntity
import hr.algebra.ecommerce.dal.product.ProductEntity
import hr.algebra.ecommerce.handler.deleteImage
import hr.algebra.ecommerce.handler.downloadAndStore
import hr.algebra.ecommerce.model.CategoryEcommerce
import hr.algebra.ecommerce.model.ProductEcommerce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EcommerceFetcher (private val context: Context) {

//    private val nasaApi: NasaApi
    private val ecommerceApi: EcommerceApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        ecommerceApi = retrofit.create(EcommerceApi::class.java)
    }

    fun fetch() {
        fetchProducts()
    }

    private fun fetchCategories() {
        val request = ecommerceApi.fetchCategories()

        request.enqueue(object: Callback<List<CategoryEcommerce>> {
            override fun onResponse(
                call: Call<List<CategoryEcommerce>>,
                response: Response<List<CategoryEcommerce>>
            ) {
                response.body()?.let { populateCategories(it) }
            }

            override fun onFailure(call: Call<List<CategoryEcommerce>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }

    private fun fetchProducts() {
        val request = ecommerceApi.fetchProducts()

        request.enqueue(object: Callback<List<ProductEcommerce>> {
            override fun onResponse(
                call: Call<List<ProductEcommerce>>,
                response: Response<List<ProductEcommerce>>
            ) {
                response.body()?.let { populateProducts(it) }
            }

            override fun onFailure(call: Call<List<ProductEcommerce>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }

    private fun populateCategories(categories: List<CategoryEcommerce>) {
        val categoryDao = AppDatabase.getInstance(context).categoryDao()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            categoryDao.deleteAllCategories()

            val categoryEntities = categories.map {
                CategoryEntity(
                    _id = it.id,
                    name = it.name,
                    image = it.image
                )
            }
            categoryDao.insertAll(categoryEntities)
        }
    }


    private fun populateProducts(products: List<ProductEcommerce>) {
        val productDao = AppDatabase.getInstance(context)
            .productDao()
        val categoriesDao = AppDatabase.getInstance(context)
            .categoryDao()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val existingProducts = productDao.getAllProducts()
            productDao.deleteAllProducts()
            categoriesDao.deleteAllCategories()

            val downloadedImagePaths = mutableListOf<String>()

            products.forEach { product ->

                val oldImagePath = existingProducts.find { it._id == product.id }?.images?.get(0)
                if (oldImagePath != null) {
                    deleteImage(oldImagePath)
                }

                val picturePath = downloadAndStore(context, product.images[0])
                if (picturePath != null) {
                    downloadedImagePaths.add(picturePath)
                }

                val productEntity = ProductEntity.getFromModel(product, picturePath)


                product.category?.let {
                    categoriesDao.insertCategory(CategoryEntity.getFromModel(it))
                }

                productDao.insertProduct(productEntity)
            }

            existingProducts.forEach { oldProduct ->
                if (products.none { it.id == oldProduct._id }) {
                    oldProduct.images.forEach { oldImagePath ->
                        deleteImage(oldImagePath)
                    }
                }
            }
            context.sendBroadcast<EcommerceReceiver>()
        }
    }

}