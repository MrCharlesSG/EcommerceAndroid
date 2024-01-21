package hr.algebra.ecommerce.model

import com.google.gson.annotations.SerializedName
import hr.algebra.ecommerce.dal.category.CategoryEntity

data class CategoryEcommerce (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("image") val image : String
){
    fun isSelected() : Boolean = categorySelected.id == this.id

    companion object{

		val defaultCategory = CategoryEcommerce(
			-1,
			"All",
			""
		)

		var categorySelected: CategoryEcommerce = defaultCategory

		fun getFromEntity(categoryEntity: CategoryEntity) = CategoryEcommerce(
			categoryEntity._id,
			categoryEntity.name,
			categoryEntity.image
		)
	}
}