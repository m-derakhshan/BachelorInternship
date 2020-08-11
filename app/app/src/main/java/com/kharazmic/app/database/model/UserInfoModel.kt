package com.kharazmic.app.database.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userInfo")
data class UserInfoModel(

    @PrimaryKey
    @ColumnInfo
    val name: String = "",

    @ColumnInfo
    val image: String = "",

    @ColumnInfo
    val worth: Int = -1,

    @ColumnInfo
    val experience: Int = -1,

    @ColumnInfo
    val education: Int = -1,

    @ColumnInfo
    val maxDays: Float = -1F,

    @ColumnInfo
    val remainingDays: Float = -1F,

    @ColumnInfo
    val subscription: String = "",

    @ColumnInfo
    val followers: String = "",

    @ColumnInfo
    val following: String = "",

    @ColumnInfo
    val signals: String = ""
)