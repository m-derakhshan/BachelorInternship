package com.kharazmic.app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kharazmic.app.database.model.UserInfoModel


@Dao
interface UserDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(info: UserInfoModel)


    @Query("DELETE FROM userInfo")
    fun deleteAll()


    @Query("UPDATE userInfo SET image = :image")
    fun updateImage(image: String)


    @Query("SELECT * FROM userInfo")
    fun getInfo(): LiveData<UserInfoModel>
}