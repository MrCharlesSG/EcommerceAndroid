package hr.algebra.ecommerce.model

import hr.algebra.ecommerce.dal.purchase.ProductPurchasedEntity

interface CartData {
    fun getTotalPrice() : Int
    fun getProductListEntity(purchaseId: Long): List<ProductPurchasedEntity>
    fun getList(): MutableList<CartElementEcommerce>
}