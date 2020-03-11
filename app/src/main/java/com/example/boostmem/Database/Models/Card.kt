package com.example.boostmem.Database.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity (tableName = "card_table",
        foreignKeys = [ForeignKey(entity = Deck::class,parentColumns = arrayOf("deckID"),childColumns = arrayOf("ownerID"),onDelete = ForeignKey.CASCADE)
]
    )
data class Card(
    @PrimaryKey (autoGenerate = true) val cardID : Long = 0,
    @ColumnInfo  val ownerID : Long,
    @ColumnInfo  var frontDesp: String,
    @ColumnInfo var backDesp: String,
    @ColumnInfo var color: Int
    ): Serializable