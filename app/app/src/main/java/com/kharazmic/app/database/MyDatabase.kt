package com.kharazmic.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kharazmic.app.database.model.*


@Database(
    entities = [UserInfoModel::class, SignalsModel::class, SearchHistoryModel::class, BestStockModel::class],
    version = 7,
    exportSchema = false
)

abstract class MyDatabase : RoomDatabase() {

    abstract val userDAO: UserDAO
    abstract val signalDAO: SignalDAO
    abstract val searchHistoryDao: SearchHistoryDAO
    abstract val bestStockDao: BestSignalDAO


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