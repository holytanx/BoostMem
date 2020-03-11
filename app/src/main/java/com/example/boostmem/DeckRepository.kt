package com.example.boostmem

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.boostmem.Database.Dao.DeckDao
import com.example.boostmem.Database.Models.Deck

class DeckRepository(private val deckDao: DeckDao) {


    val allDecks: LiveData<List<Deck>> = deckDao.getAllDecks()

    @WorkerThread
    fun insert(deck : Deck) {
        deckDao.insert(deck)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(deckDao).execute()
    }

    private class deleteAllWordsAsyncTask internal constructor(private val mAsyncTaskDao: DeckDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteDeck(deck:Deck) {
        deleteWordAsyncTask(deckDao).execute(deck)
    }

    private class deleteWordAsyncTask internal constructor(private val mAsyncTaskDao: DeckDao) :
        AsyncTask<Deck, Void, Void>() {

        override fun doInBackground(vararg params: Deck): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

    /* -------------- ACTUALIZAR UN SOLO DATO ---------------- */

    fun update(deck: Deck) {
        updateDeckAsyncTask(deckDao).execute(deck)
    }

    private class updateDeckAsyncTask internal constructor(private val mAsyncTaskDao: DeckDao) :
        AsyncTask<Deck, Void, Void>() {
        override fun doInBackground(vararg params: Deck?): Void? {
            mAsyncTaskDao.update(params[0]!!)
            return null
        }
    }
}