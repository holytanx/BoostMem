package com.example.boostmem.Database.Repository

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.boostmem.Database.Dao.CardDao
import com.example.boostmem.Database.Dao.CategoryDao
import com.example.boostmem.Database.Dao.DeckDao
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.Database.Models.Category
import com.example.boostmem.Database.Models.Deck


class CardRepository (private val cardDao: CardDao){
    val allCards :LiveData<List<Card>> = cardDao.getAllCards()

    @WorkerThread
    fun insert(card:Card){
        cardDao.insert(card)
    }
    fun deleteAll() {
        deleteAllCardsAsyncTask(cardDao).execute()
    }

    private class deleteAllCardsAsyncTask internal constructor(private val mAsyncTaskDao: CardDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteCard(card: Card) {
        deleteCardsyncTask(cardDao).execute(card)
    }

    private class deleteCardsyncTask internal constructor(private val mAsyncTaskDao: CardDao) :
        AsyncTask<Card, Void, Void>() {

        override fun doInBackground(vararg params: Card): Void? {
            mAsyncTaskDao.deleteCard(params[0])
            return null
        }
    }

    fun update(card: Card) {
        updateCardAsyncTask(cardDao).execute(card)
    }

    private class updateCardAsyncTask internal constructor(private val mAsyncTaskDao: CardDao) :
        AsyncTask<Card, Void, Void>() {
        override fun doInBackground(vararg params: Card?): Void? {
            mAsyncTaskDao.update(params[0]!!)
            return null
        }
    }
}