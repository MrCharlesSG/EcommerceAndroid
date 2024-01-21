package hr.algebra.ecommerce.dal.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import hr.algebra.ecommerce.model.CategoryEcommerce

@Entity
data class CategoryEntity(
    @PrimaryKey val _id: Int,
    val name: String,
    val image: String
) {
    companion object {
        fun getFromModel(category: CategoryEcommerce) = CategoryEntity(
            category.id,
            category.name,
            category.image
        )
    }
}