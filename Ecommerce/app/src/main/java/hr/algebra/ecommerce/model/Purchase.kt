package hr.algebra.ecommerce.model

import hr.algebra.ecommerce.dal.purchase.ProductPurchasedEntity
import hr.algebra.ecommerce.dal.purchase.PurchaseEntity

data class Purchase(
    val _id: Long = 0,
    var price: Int = 0,
    val products: MutableList<CartElementEcommerce> = mutableListOf()
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
            }.toMutableList()
        )

        fun getFromEntity(purchasesEntity: PurchaseEntity) = Purchase(
            purchasesEntity._id,
            purchasesEntity.price
        )
    }
}
