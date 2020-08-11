package com.kharazmic.app.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kharazmic.app.database.model.UserInfoModel


@Dao
interface UserDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(info: UserInfoModel)


    @Query("DELETE FROM userInfo")
    fun deleteAll()


    @Query("SELECT * FROM userInfo")
    fun getInfo(): LiveData<UserInfoModel>
}