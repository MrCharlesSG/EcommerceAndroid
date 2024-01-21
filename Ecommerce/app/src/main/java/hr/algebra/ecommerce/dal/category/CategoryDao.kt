package hr.algebra.ecommerce.dal.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity")
    fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryEntity)

    @Delete
    fun deleteCategory(category: CategoryEntity)

    @Query("select * from CategoryEntity where _id=:id")
    fun getCategory(id:Int) : CategoryEntity?

    @Query("DELETE FROM CategoryEntity")
    fun deleteAllCategories()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<CategoryEntity>)
}