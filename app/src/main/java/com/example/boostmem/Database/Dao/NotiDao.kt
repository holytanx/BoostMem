package com.example.boostmem.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.boostmem.Database.Models.NotificationModel
@Dao
interface NotiDao {
    // MOSTRAR DATOS Y ORDENARLOS
    @Query("SELECT * from notification_table ORDER BY notificationID ASC")
    fun getAllNoti(): LiveData<List<NotificationModel>>

    // AÑADIR DATOS
    @Insert
    fun insert(notificationModel: NotificationModel)

    // ELIMINAR ALL DATA
    @Query("DELETE FROM notification_table")
    fun deleteAll()

    // ACTUALIZAR DATOS
    //Sin Query
    @Update
    fun update(notificationModel: NotificationModel)


    //Con Query
    @Query("UPDATE notification_table SET isActive = :isActive WHERE notificationID == :id")
    fun updateNOti(isActive: Boolean, id: Long)

    // BORRAR ITEM
    @Delete
    fun deleteNoti(notificationModel: NotificationModel)

}