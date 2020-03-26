package com.example.boostmem.Database.Models

import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "deck_table")

class Deck(
    @PrimaryKey (autoGenerate = true) var deckID : Long = 0,
    var deckName:String,
    var cateID:Int = 0,
    var description:String
) : Serializable