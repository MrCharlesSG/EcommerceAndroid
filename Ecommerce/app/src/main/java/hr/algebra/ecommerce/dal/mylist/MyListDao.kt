package hr.algebra.ecommerce.dal.mylist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyListDao {
    @Query("SELECT * FROM MyListEntity")
    fun getAll(): List<MyListEntity>

    @Query("SELECT * FROM MyListEntity WHERE _id = :id")
    fun get(id: Int): MyListEntity?

    @Insert
    fun insert(myListEntity: MyListEntity)

    @Delete
    fun delete(myListEntity: MyListEntity)
}