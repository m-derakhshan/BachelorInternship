package com.kharazmic.app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kharazmic.app.main.profile.SignalsModel


@Dao
interface SignalDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(signalsModel: SignalsModel)


    @Query("SELECT * FROM Signals WHERE category = :category")
    fun getInfo(category: String): LiveData<List<SignalsModel>>


}