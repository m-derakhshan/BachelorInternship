package com.kharazmic.app.main.profile.signals

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
    val analyzer: String,

    @ColumnInfo
    val analyzerId: String,


    @ColumnInfo
    val title: String,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val publishDate: String,

    @ColumnInfo
    val waitingDate: String,

    @ColumnInfo
    val profit: String,

    @ColumnInfo
    val loss: String


)