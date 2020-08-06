package com.kharazmic.app.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(info: UserInfoModel)




    @Query("SELECT * FROM userInfo")
    fun getInfo(): LiveData<UserInfoModel>
}