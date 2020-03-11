package com.example.boostmem.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.boostmem.Database.Models.Deck

@Dao
interface DeckDao{

    // MOSTRAR DATOS Y ORDENARLOS
    @Query("SELECT * from deck_table ORDER BY deckID ASC")
    fun getAllDecks(): LiveData<List<Deck>>

    // AÑADIR DATOS
    @Insert
    fun insert(deck:Deck)

    // ELIMINAR ALL DATA
    @Query("DELETE FROM deck_table")
    fun deleteAll()

    // ACTUALIZAR DATOS
    //Sin Query
    @Update
    fun update(deck: Deck)

//    //Con Query
//    @Query("UPDATE decks SET deck = :word WHERE id == :id")
//    fun updateItem(word: String, id: Int)

    // BORRAR ITEM
    @Delete
    fun deleteWord(deck: Deck)

}