package com.kharazmic.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kharazmic.app.database.model.SearchHistoryModel
import com.kharazmic.app.database.model.SignalsModel
import com.kharazmic.app.database.model.UserInfoModel


@Database(
    entities = [UserInfoModel::class, SignalsModel::class, SearchHistoryModel::class],
    version = 2,
    exportSchema = false
)

abstract class MyDatabase : RoomDatabase() {

    abstract val userDAO: UserDAO
    abstract val signalDAO: SignalDAO
    abstract val searchHistoryDao: SearchHistoryDAO


    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}