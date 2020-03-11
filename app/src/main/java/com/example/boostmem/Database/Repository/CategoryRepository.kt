package com.example.boostmem.Database.Repository

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.boostmem.Database.Dao.CategoryDao
import com.example.boostmem.Database.Models.Category

class CategoryRepository(private val cateDao: CategoryDao) {


    val allCates: LiveData<List<Category>> = cateDao.getAllCategories()

    @WorkerThread
    fun insert(cate : Category) {
        cateDao.insert(cate)
    }



    fun deleteAll() {
        deleteAllDecksAsyncTask(cateDao).execute()
    }

    private class deleteAllDecksAsyncTask internal constructor(private val mAsyncTaskDao: CategoryDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteCate(cate: Category) {
        deleteDeckAsyncTask(cateDao).execute(cate)
    }

    private class deleteDeckAsyncTask internal constructor(private val mAsyncTaskDao: CategoryDao) :
        AsyncTask<Category, Void, Void>() {

        override fun doInBackground(vararg params: Category?): Void? {
            mAsyncTaskDao.deleteCate(params[0])
            return null
        }
    }

    /* -------------- ACTUALIZAR UN SOLO DATO ---------------- */

//    fun update(deck: Deck) {
//        updateWordAsyncTask(deckDao).execute(deck)
//    }

//    private class updateWordAsyncTask internal constructor(private val mAsyncTaskDao: DeckDao) :
//        AsyncTask<Deck, Void, Void>() {
//        override fun doInBackground(vararg params: Deck?): Void? {
//            mAsyncTaskDao.updateDeck(params[0]!!)
//            return null
//        }
//    }
}