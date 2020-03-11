package com.example.boostmem.Database.Repository

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.boostmem.Database.Dao.NotiDao
import com.example.boostmem.Database.Models.NotificationModel

class NotiRepository (private val notiDao: NotiDao){
    val allNoti : LiveData<List<NotificationModel>> = notiDao.getAllNoti()

    @WorkerThread
    fun insert(notificationModel: NotificationModel){
        notiDao.insert(notificationModel)
    }
    fun deleteAll() {
        deleteAllNotiAsyncTask(notiDao).execute()
    }

    private class deleteAllNotiAsyncTask internal constructor(private val mAsyncTaskDao: NotiDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteNoti(notificationModel: NotificationModel) {
        deleteNotiyncTask(notiDao).execute(notificationModel)
    }

    private class deleteNotiyncTask internal constructor(private val mAsyncTaskDao: NotiDao) :
        AsyncTask<NotificationModel, Void, Void>() {

        override fun doInBackground(vararg params: NotificationModel): Void? {
            mAsyncTaskDao.deleteNoti(params[0])
            return null
        }
    }

    fun update(notificationModel: NotificationModel) {
        updateNotiAsyncTask(notiDao).execute(notificationModel)
    }

    fun updateIsActive(notificationModel: MyTaskParams){
        updateIsActiveAsyncTask(notiDao).execute(notificationModel)
    }

    private class updateNotiAsyncTask internal constructor(private val mAsyncTaskDao: NotiDao) :
        AsyncTask<NotificationModel, Void, Void>() {
        override fun doInBackground(vararg params: NotificationModel?): Void? {
            mAsyncTaskDao.update(params[0]!!)
            return null
        }
    }
    private class updateIsActiveAsyncTask internal constructor(private val mAsyncTaskDao: NotiDao) :
        AsyncTask<MyTaskParams, Void, Void>() {
        override fun doInBackground(vararg params: MyTaskParams?): Void? {
            mAsyncTaskDao.updateNOti(params[0]!!.active,params[0]!!.id)
            return null
        }
    }

    data class MyTaskParams(var active:Boolean, var id:Long)

}