package hr.algebra.ecommerce.dal.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartEntity(
    @PrimaryKey val _id: Int,
    val quantity: Int
)
