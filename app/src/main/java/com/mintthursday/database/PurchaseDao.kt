package com.mintthursday.database

import androidx.room.*
import com.mintthursday.models.Purchase


@Dao
interface PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPurchase(purchases: List<Purchase>)

    @Update
    fun updatePurchase(purchase: Purchase)

    @Delete
    fun deletePurchase(purchase: Purchase?)

    @Query("SELECT * FROM purchase")
    fun getIngredientList(): List<Purchase>


}