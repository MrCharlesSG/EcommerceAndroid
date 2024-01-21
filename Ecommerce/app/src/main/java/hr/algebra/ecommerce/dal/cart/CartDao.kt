package hr.algebra.ecommerce.dal.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CartDao {
    @Query("SELECT * FROM CartEntity")
    fun getCart(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(cart: CartEntity)

    // Nueva función para aumentar la cantidad
    @Transaction
    fun addToCart(cart: CartEntity) {
        val existingCartItem = getCartItem(cart._id)

        if (existingCartItem != null) {
            increaseQuantity(cart._id, cart.quantity)
        } else {
            add(cart)
        }
    }

    @Query("UPDATE CartEntity SET quantity = quantity + :incrementAmount WHERE _id = :productId")
    fun increaseQuantity(productId: Int, incrementAmount: Int)

    @Query("SELECT * FROM CartEntity WHERE _id = :productId")
    fun getCartItem(productId: Int): CartEntity?

    @Delete
    fun delete(cart: CartEntity)

    @Query("DELETE FROM CartEntity")
    fun resetCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<CartEntity>)
}