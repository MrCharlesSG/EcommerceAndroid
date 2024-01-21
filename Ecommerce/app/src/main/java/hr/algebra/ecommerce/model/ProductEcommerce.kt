package hr.algebra.ecommerce.model

import com.google.gson.annotations.SerializedName
import hr.algebra.ecommerce.dal.product.ProductEntity
import hr.algebra.ecommerce.dal.purchase.ProductPurchasedEntity

data class ProductEcommerce (

	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("price") val price : Int,
	@SerializedName("description") val description : String,
	@SerializedName("images") val images : List<String>,
	@SerializedName("category") val category : CategoryEcommerce?=null,
	private var inMyList : Boolean ?= false
) {

	fun isInMyList() : Boolean = inMyList ?: false
	fun setInMyList(inMyList: Boolean) {this.inMyList=inMyList}


    companion object {
        fun getFromEntity(productEntity: ProductEntity): ProductEcommerce =
			ProductEcommerce(
				id = productEntity._id,
				title = productEntity.title,
				price = productEntity.price,
				description = productEntity.description,
				images = productEntity.images,
				category = CategoryEcommerce(
					id = productEntity.category._id,
					name = productEntity.category.name,
					image = productEntity.category.image
				)
			)

        fun getFromPurchaseEntity(productPurchasedEntity: ProductPurchasedEntity) : ProductEcommerce =
			ProductEcommerce(
				id = productPurchasedEntity._id ?: -1,
				title = productPurchasedEntity.title,
				price = productPurchasedEntity.price,
				description = productPurchasedEntity.description,
				images = productPurchasedEntity.images
			)
    }
}