package com.example.boostmem.Database.Repository

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.boostmem.Database.Dao.StatisticDao
import com.example.boostmem.Database.Models.Statistic

class StatisticRepository (private val statisticDao: StatisticDao){
    val allStatistics : LiveData<List<Statistic>> = statisticDao.getAllStatistics()

    @WorkerThread
    fun insert(statistic: Statistic){
        statisticDao.insert(statistic)
    }
    fun deleteAll() {
        deleteAllNotiAsyncTask(statisticDao).execute()
    }

    private class deleteAllNotiAsyncTask internal constructor(private val mAsyncTaskDao: StatisticDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteStatistic(statistic: Statistic) {
        deleteNotiyncTask(statisticDao).execute(statistic)
    }

    private class deleteNotiyncTask internal constructor(private val mAsyncTaskDao: StatisticDao) :
        AsyncTask<Statistic, Void, Void>() {

        override fun doInBackground(vararg params: Statistic): Void? {
            mAsyncTaskDao.delete(params[0])
            return null
        }
    }

    fun update(statistic: Statistic) {
        updateStatisticAsynTask(statisticDao).execute(statistic)
    }


    private class updateStatisticAsynTask internal constructor(private val mAsyncTaskDao: StatisticDao) :
        AsyncTask<Statistic, Void, Void>() {
        override fun doInBackground(vararg params: Statistic?): Void? {
            mAsyncTaskDao.update(params[0]!!)
            return null
        }
    }
}