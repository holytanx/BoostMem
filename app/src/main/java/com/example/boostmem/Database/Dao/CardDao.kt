package com.example.boostmem.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.boostmem.Database.Models.Card

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: Card)

    @Query("DELETE FROM card_table")
    fun deleteAll()

    @Query("SELECT * from card_table ORDER BY cardID ASC")
    fun getAllCards(): LiveData<List<Card>>

    @Query("SELECT COUNT(cardID) FROM card_table")
    fun getDataCount(): Int

    @Delete
    fun deleteCard(card: Card)

    @Update
    fun update(card: Card)
}