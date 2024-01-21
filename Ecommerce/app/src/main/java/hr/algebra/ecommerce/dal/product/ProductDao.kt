package hr.algebra.ecommerce.dal.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    fun getAllProducts(): List<ProductEntity>

    @Insert
    fun insertProduct(product: ProductEntity)

    @Delete
    fun deleteProduct(product: ProductEntity)

    @Query("select * from ProductEntity where _id=:id")
    fun getProduct(id:Int) : ProductEntity?

    @Query("DELETE FROM ProductEntity")
    fun deleteAllProducts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity WHERE _id IN (:ids)")
    fun getProductsByIds(ids: List<Int>): List<ProductEntity>
}