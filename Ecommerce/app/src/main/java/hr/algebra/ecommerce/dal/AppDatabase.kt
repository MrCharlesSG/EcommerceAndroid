package hr.algebra.ecommerce.dal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hr.algebra.ecommerce.dal.cart.CartDao
import hr.algebra.ecommerce.dal.cart.CartEntity
import hr.algebra.ecommerce.dal.category.CategoryDao
import hr.algebra.ecommerce.dal.category.CategoryEntity
import hr.algebra.ecommerce.dal.converters.CartListConverter
import hr.algebra.ecommerce.dal.converters.DateConverter
import hr.algebra.ecommerce.dal.converters.ImagesConverter
import hr.algebra.ecommerce.dal.mylist.MyListDao
import hr.algebra.ecommerce.dal.mylist.MyListEntity
import hr.algebra.ecommerce.dal.product.ProductDao
import hr.algebra.ecommerce.dal.product.ProductEntity
import hr.algebra.ecommerce.dal.purchase.ProductPurchasedEntity
import hr.algebra.ecommerce.dal.purchase.PurchaseDao
import hr.algebra.ecommerce.dal.purchase.PurchaseEntity

@Database(entities = [ProductEntity::class, CategoryEntity::class, CartEntity::class, PurchaseEntity::class, ProductPurchasedEntity::class, MyListEntity::class], version = 9, exportSchema = false)
@TypeConverters(ImagesConverter::class, DateConverter::class, CartListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cartDao(): CartDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun myListDao(): MyListDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(AppDatabase::class.java) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "ecommerce.db"
            )
                //.addMigrations(migration_2_3)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
