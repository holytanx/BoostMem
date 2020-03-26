package com.example.boostmem.Database.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "statistics",  foreignKeys = [ForeignKey(entity = Deck::class,parentColumns = arrayOf("deckID"),childColumns = arrayOf("deckownerID"),onDelete = ForeignKey.CASCADE)])
data class Statistic(
    @PrimaryKey (autoGenerate = true) var statisticID: Long,
    @ColumnInfo val deckownerID : Long,
    @ColumnInfo var average: Float,
    @ColumnInfo var playCount: Int

) {
    override fun toString(): String {
        return "Statistic: ${statisticID} owner: {$deckownerID} average: ${average} count: ${playCount} "
    }
}