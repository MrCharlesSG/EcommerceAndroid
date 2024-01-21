package hr.algebra.ecommerce.model

import android.content.Context
import hr.algebra.ecommerce.dal.purchase.ProductPurchasedEntity
import hr.algebra.ecommerce.handler.changeImageName

data class Cart(
    private var total: Int,
    private val context: Context,
    private val cart: MutableList<CartElementEcommerce>
) {
    fun addToCart(cartElementEcommerce: CartElementEcommerce) {
        var exists = false
        cart.forEach {
            if (it._id == cartElementEcommerce._id) {
                exists = true
                it.quantity += cartElementEcommerce.quantity
            }
        }
        if (!exists) {
            cart.add(cartElementEcommerce)
        }
        total += cartElementEcommerce.quantity * cartElementEcommerce.product.price
    }

    fun remove(cartEntity: CartElementEcommerce) {
        total -= cartEntity.quantity * cartEntity.product.price
        cart.removeIf {
            it._id == cartEntity._id
        }
    }

    fun getList(): MutableList<CartElementEcommerce> = cart
    fun getTotalPrice(): Int = total
    fun clear() {
        cart.clear()
        total = 0
    }

    fun changeQuantity(cartElementEcommerce: CartElementEcommerce, newQuantity: Int) {
        if (newQuantity == 0) {
            remove(cartElementEcommerce)
        } else {
            total += (newQuantity - cartElementEcommerce.quantity) * cartElementEcommerce.product.price
            cartElementEcommerce.quantity = newQuantity
        }
    }

    fun getProductListEntity(purchaseId: Long): List<ProductPurchasedEntity> {
        val list = mutableListOf<ProductPurchasedEntity>()
        cart.forEach { element ->
            val images = mutableListOf<String>()
            element.product.images.forEach { image ->
                val picturePath = changeImageName(context, image, getCorrectUrl(purchaseId, image))
                if (picturePath != null)
                    images.add(picturePath)
            }
            list.add(
                ProductPurchasedEntity(
                    null,
                    element.product.title,
                    element.product.price,
                    element.product.description,
                    images,
                    purchaseId,
                    element.quantity
                )
            )

        }
        return list
    }

    private fun getCorrectUrl(purchaseId: Long, image: String): String {
        val extension: String = image.substring(image.lastIndexOf('.'))
        val urlWithoutExtension: String = image.substring(0, image.lastIndexOf('.'))
        return urlWithoutExtension + purchaseId + extension
    }
}


