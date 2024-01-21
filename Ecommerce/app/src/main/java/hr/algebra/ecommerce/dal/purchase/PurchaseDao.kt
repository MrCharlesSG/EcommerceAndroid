package hr.algebra.ecommerce.dal.purchase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM PurchaseEntity")
    fun getAllPurchases(): List<PurchaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPurchase(purchaseEntity: PurchaseEntity)

    @Query("SELECT * FROM PurchaseEntity WHERE _id = :id")
    fun getPurchaseById(id: Long): PurchaseEntity?

    @Insert
    fun insertProducts(productsPurchasedEntity: List<ProductPurchasedEntity>)

    @Query("Select * from ProductPurchasedEntity where id_purchase=:id ")
    fun getAllProductOfPurchase(id:Long) : List<ProductPurchasedEntity>
}

