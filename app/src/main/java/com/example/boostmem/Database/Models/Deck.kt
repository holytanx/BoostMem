package com.example.boostmem.Database.Models

import androidx.room.*
import java.io.Serializable
//@Entity(tableName = "deck_table",
//    foreignKeys = arrayOf(ForeignKey(
//        entity = Category::class,
//        parentColumns = arrayOf("categoryID"),
//        childColumns = arrayOf("cateID"),
//        onDelete = ForeignKey.CASCADE
//    )),indices = (arrayOf(Index(value = [ "cateID" ], unique = true))))

@Entity(
    tableName = "deck_table"
//    foreignKeys =
//[
//    ForeignKey(
//        entity = Category::class,
//        parentColumns = arrayOf("categoryID"),
//        childColumns = arrayOf("cateID"),
//        onDelete = ForeignKey.CASCADE
//    )
//], indices = [Index(value = ["cateID"])]
)

class Deck(
    @PrimaryKey (autoGenerate = true) var deckID : Long = 0,
    var deckName:String,
    var cateID:Int = 0,
    var description:String
) : Serializable
{

}
//@Entity(tableName = "word_table")
//class Word(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    @ColumnInfo(name = "word") var word: String)