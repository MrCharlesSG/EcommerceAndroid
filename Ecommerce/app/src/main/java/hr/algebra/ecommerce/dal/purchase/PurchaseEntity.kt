package hr.algebra.ecommerce.dal.purchase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PurchaseEntity (
    @PrimaryKey
    val _id: Long,
    var price: Int
)