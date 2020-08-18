package com.kharazmic.app.database.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface BestSignalDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(model: BestStockModel)


    @Query("Select * from bestStock where category = :category")
    fun getStock(category: String): LiveData<List<BestStockModel>>


}