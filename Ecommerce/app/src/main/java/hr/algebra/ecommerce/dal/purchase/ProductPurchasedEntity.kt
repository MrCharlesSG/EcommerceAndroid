package hr.algebra.ecommerce.dal.purchase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductPurchasedEntity (
    @PrimaryKey(autoGenerate = true)
    var _id: Int? = null,
    val title: String,
    val price: Int,
    val description: String,
    val images: List<String>,
    val id_purchase: Long,
    val quantity : Int
)