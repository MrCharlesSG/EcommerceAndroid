package hr.algebra.ecommerce.services

import android.content.Context
import hr.algebra.ecommerce.dal.cart.CartEntity
import hr.algebra.ecommerce.model.Cart
import hr.algebra.ecommerce.model.CartData
import hr.algebra.ecommerce.model.CartElementEcommerce
import hr.algebra.ecommerce.model.ProductEcommerce

class CartAS(private val context: Context) {

    private var cart: Cart = Cart(0, context, mutableListOf())

    fun addToCart(cartEntity: CartEntity, product: ProductEcommerce) {
        cart.addToCart(
            CartElementEcommerce(
                cartEntity._id,
                cartEntity.quantity,
                product
            )
        )
    }

    fun removeFromCart(cartEntity: CartEntity, product: ProductEcommerce) {
        cart.remove(
            CartElementEcommerce(
                cartEntity._id,
                cartEntity.quantity,
                product
            )
        )
    }

    fun clearCart() {
        cart.clear()
    }

    fun updateCartMyList(product: ProductEcommerce) {
        cart.getList().forEach {
            if (it._id == product.id) {
                it.product.setInMyList(product.isInMyList())
            }
        }
    }

    fun getCart(): CartData = cart
    fun changeQuantity(cartElement: CartElementEcommerce, newQuantity: Int) {
        cart.changeQuantity(cartElement, newQuantity)
    }

}