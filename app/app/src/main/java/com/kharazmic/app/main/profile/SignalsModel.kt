package com.kharazmic.app.main.profile

import androidx.room.ColumnInfo
import androidx.room.Entity



@Entity(tableName = "Signals", primaryKeys = ["id", "category"])
data class SignalsModel(


    @ColumnInfo
    val id: String,


    @ColumnInfo
    val category: String,

    @ColumnInfo
    val group: String,

    @ColumnInfo
    val author: String,

    @ColumnInfo
    val realtimeProfit: String,

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val date: String,

    @ColumnInfo
    val profit: String,

    @ColumnInfo
    val loss: String
)