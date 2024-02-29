package hr.algebra.ecommerce.services

import android.content.Context
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.category.CategoryDao
import hr.algebra.ecommerce.model.CategoryEcommerce

class CategorySA(context: Context) {
    fun getCategories() : List<CategoryEcommerce> {
        val categoriesMutable = mutableListOf<CategoryEcommerce>()
        categoriesMutable.add(CategoryEcommerce.defaultCategory)
        val categoryEntities = categoryDao.getAllCategories()
        categoryEntities.forEach {
            categoriesMutable.add(CategoryEcommerce.getFromEntity(it))
        }
        return categoriesMutable
    }

    private val categoryDao: CategoryDao = AppDatabase.getInstance(context)
        .categoryDao()
}