package hr.algebra.ecommerce.model

import hr.algebra.ecommerce.dal.purchase.ProductPurchasedEntity

data class CartElementEcommerce(
    val _id: Int = 0,
    var quantity: Int = 0,
    val product: ProductEcommerce = ProductEcommerce()
) {
    companion object {
        fun getFromPurchaseEntity(productPurchasedEntity: ProductPurchasedEntity) =
            CartElementEcommerce(
                productPurchasedEntity._id ?: -1,
                productPurchasedEntity.quantity,
                ProductEcommerce(
                    id = productPurchasedEntity._id ?: -1,
                    title = productPurchasedEntity.title,
                    price = productPurchasedEntity.price,
                    description = productPurchasedEntity.description,
                    images = productPurchasedEntity.images
                )
            )
    }

}