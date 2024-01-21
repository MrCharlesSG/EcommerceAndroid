package hr.algebra.ecommerce.model

import hr.algebra.ecommerce.dal.purchase.ProductPurchasedEntity
import hr.algebra.ecommerce.dal.purchase.PurchaseEntity

data class Purchase(
    val _id: Long,
    var price: Int,
    val products: List<CartElementEcommerce> ? = null
) {
    companion object {
        fun getFromEntity(
            purchasesEntity: PurchaseEntity,
            productsEntities: List<ProductPurchasedEntity>
        ) = Purchase(
            purchasesEntity._id,
            purchasesEntity.price,
            productsEntities.map { productPurchasedEntity ->
                CartElementEcommerce.getFromPurchaseEntity(productPurchasedEntity)
            }
        )

        fun getFromEntity(purchasesEntity: PurchaseEntity) = Purchase(
            purchasesEntity._id,
            purchasesEntity.price
        )
    }
}
