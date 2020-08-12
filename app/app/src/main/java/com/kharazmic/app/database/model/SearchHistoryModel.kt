package com.kharazmic.app.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "SearchHistoryModel")
data class SearchHistoryModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val number: Int = 0,

    @ColumnInfo
    val id: String,


    @ColumnInfo
    val name: String


)