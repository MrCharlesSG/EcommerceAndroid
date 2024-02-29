package hr.algebra.ecommerce.services

import android.content.Context
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.dal.AppDatabase
import hr.algebra.ecommerce.dal.purchase.PurchaseEntity
import hr.algebra.ecommerce.model.Purchase
import java.time.LocalDate

class PurchasesAS(context: Context) : ApplicationAuthenticatedService(context) {
    private val purchaseDao = AppDatabase.getInstance(context)
        .purchaseDao()
    private val saCart = (context.applicationContext as App).getCartAS()

    fun getAllPurchases(): List<Purchase> =
        purchaseDao.getAllPurchases()
            .map { purchaseEntity ->
                val productsEntities = purchaseDao.getAllProductOfPurchase(purchaseEntity._id)
                Purchase.getFromEntity(purchaseEntity, productsEntities)
            }

    fun getAllPurchasesAndTotal(): Pair<Int, List<Purchase>> {
        var totalExpended = 0
        val list = purchaseDao.getAllPurchases()
            .map { purchaseEntity ->
                totalExpended += purchaseEntity.price
                Purchase.getFromEntity(purchaseEntity)
            }
        return Pair(totalExpended, list)
    }

    suspend fun buy() : Boolean =
        authenticateFunction {
            val cart = saCart.getCart()
            val purchaseID = LocalDate.now().toEpochDay()
            val purchaseEntity = PurchaseEntity(purchaseID, cart.getTotalPrice())
            val productListEntity = cart.getProductListEntity(purchaseID)

            val purchaseEntityFromDB = purchaseDao.getPurchaseById(purchaseID)
            if (purchaseEntityFromDB != null) {
                purchaseEntity.price += purchaseEntityFromDB.price
            }
            purchaseDao.insertPurchase(purchaseEntity)
            purchaseDao.insertProducts(productListEntity)

        }

}