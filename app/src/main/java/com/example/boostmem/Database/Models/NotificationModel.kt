package com.example.boostmem.Database.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity (tableName = "notification_table")
data class NotificationModel(
    @PrimaryKey (autoGenerate = true) val notificationID: Long,
    @ColumnInfo var deckName:String,
    @ColumnInfo  var isActive:Boolean = true,
    @ColumnInfo var time:String,
    @ColumnInfo  var dayList:ArrayList<String>?) : Serializable {

    override fun toString(): String {
        return "id: $notificationID , deck: $deckName , isActive: $isActive, time: $time, dayList : $dayList"
    }
}