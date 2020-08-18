package com.kharazmic.app.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bestStock")
data class BestStockModel(
    @PrimaryKey
    @ColumnInfo
    val id: String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val full_name: String,

    @ColumnInfo
    val price: String,

    @ColumnInfo
    val profit: Double,


    @ColumnInfo
    val category: String,

    @ColumnInfo
    val lastUpdate: String
)