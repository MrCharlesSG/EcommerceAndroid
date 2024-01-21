package hr.algebra.ecommerce.dal.product

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hr.algebra.ecommerce.dal.category.CategoryEntity
import hr.algebra.ecommerce.model.ProductEcommerce

@Entity
data class ProductEntity(
    @PrimaryKey val _id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val images: List<String>,

    @Embedded(prefix = "category_")
    val category: CategoryEntity
) {
    companion object {
        fun getFromModel(product: ProductEcommerce, picturePath: String?) = ProductEntity(
            _id = product.id,
            title = product.title,
            price = product.price,
            description = product.description,
            images = listOf(picturePath ?: ""),
            category = CategoryEntity(
                _id = product.category?.id ?: -1,
                name = product.category?.name ?: "",
                image = product.category?.image ?:""
            )
        )
    }
}