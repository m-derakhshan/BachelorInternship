package com.kharazmic.app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kharazmic.app.database.model.SignalsModel


@Dao
interface SignalDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(signalsModel: SignalsModel)



    @Query("Update signals SET realTimeProfit = :profit where id = :id")
    fun addProfit(id: String, profit: String)


    @Query("DELETE FROM Signals")
    fun deleteAll()


    @Query("SELECT * FROM Signals WHERE category = :category")
    fun getCategoryInfo(category: String): LiveData<List<SignalsModel>>


    @Query("SELECT * FROM Signals WHERE category = :category AND id = :id")
    fun getStockInfo(category: String, id: String): LiveData<SignalsModel>


}