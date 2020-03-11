package com.example.boostmem.Database.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "statistics",
    foreignKeys = [ForeignKey(entity = Deck::class,parentColumns = arrayOf("deckID"),childColumns = arrayOf("deckownerID"),onDelete = ForeignKey.CASCADE)])
data class Statistic(
    @PrimaryKey (autoGenerate = true) var statisticID: Long,
    @ColumnInfo val deckownerID : Long,
    @ColumnInfo var gradeA : Int,
    @ColumnInfo var gradeB : Int,
    @ColumnInfo var gradeC : Int,
    @ColumnInfo var gradeD : Int,
    @ColumnInfo var gradeF : Int,
    @ColumnInfo var correct : Int,
    @ColumnInfo var incorrect: Int

)