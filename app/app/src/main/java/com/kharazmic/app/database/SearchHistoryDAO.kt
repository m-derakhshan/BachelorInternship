package com.kharazmic.app.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kharazmic.app.database.model.SearchHistoryModel


@Dao
interface SearchHistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(stock: SearchHistoryModel)


    @Query("SELECT * FROM SearchHistoryModel Order by number DESC")
    fun getAll(): LiveData<List<SearchHistoryModel>>


    @Query("DELETE FROM SearchHistoryModel WHERE number = :id")
    fun delete(id: Int)

}