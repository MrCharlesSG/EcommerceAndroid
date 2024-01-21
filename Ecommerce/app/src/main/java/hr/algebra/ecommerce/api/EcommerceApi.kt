package hr.algebra.ecommerce.api

import hr.algebra.ecommerce.model.CategoryEcommerce
import hr.algebra.ecommerce.model.ProductEcommerce
import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://api.escuelajs.co/api/v1/"

interface EcommerceApi{

    @GET("products")
    fun fetchProducts() : Call<List<ProductEcommerce>>

    @GET("categories")
    fun fetchCategories() : Call<List<CategoryEcommerce>>
}