package hr.algebra.ecommerce.services

import android.content.Context
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.auth.AuthManagerRepository
import hr.algebra.ecommerce.dal.cart.CartEntity
import hr.algebra.ecommerce.model.Cart
import hr.algebra.ecommerce.model.CartElementEcommerce
import hr.algebra.ecommerce.model.ProductEcommerce

class CartAS (private val context: Context) {

    private var cart: Cart = Cart(0, context, mutableListOf())

    fun addToCart(cartEntity: CartEntity, product: ProductEcommerce) {
        if(AuthManagerRepository.INSTANCE.getAuthManager().isUserLogged()){
            cart.addToCart(
                CartElementEcommerce(
                    cartEntity._id,
                    cartEntity.quantity,
                    product
                )
            )
        }else{
            (context.applicationContext as App).showNotAuthenticatedDialog()
        }
    }

    fun removeFromCart(cartEntity: CartEntity, product: ProductEcommerce) {
        cart.remove(CartElementEcommerce(
            cartEntity._id,
            cartEntity.quantity,
            product
        ))
    }

    fun clearCart() {
        cart.clear()
    }

    fun updateCartMyList(product: ProductEcommerce) {
        cart.getList().forEach {
            if(it._id== product.id){
                it.product.setInMyList(product.isInMyList())
            }
        }
    }

    fun getCart(): Cart = cart

}