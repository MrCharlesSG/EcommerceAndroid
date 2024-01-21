package hr.algebra.ecommerce.dal.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hr.algebra.ecommerce.dal.cart.CartEntity

class CartListConverter {
    @TypeConverter
    fun fromCartList(cartList: List<CartEntity>?): String {
        val gson = Gson()
        return gson.toJson(cartList)
    }

    @TypeConverter
    fun toCartList(cartListString: String?): List<CartEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<CartEntity>>() {}.type
        return gson.fromJson(cartListString, type)
    }
}
