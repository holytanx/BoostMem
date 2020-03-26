package com.example.boostmem.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.boostmem.Database.Models.Statistic

@Dao
interface StatisticDao {
    // MOSTRAR DATOS Y ORDENARLOS
    @Query("SELECT * from statistics ORDER BY statisticID ASC")
    fun getAllStatistics(): LiveData<List<Statistic>>

    // AÑADIR DATOS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(statistic: Statistic)

    // ELIMINAR ALL DATA
    @Query("DELETE FROM statistics")
    fun deleteAll()

    // ACTUALIZAR DATOS
    //Sin Query
    @Update
    fun update(statistic: Statistic)


    // BORRAR ITEM
    @Delete
    fun delete(statistic: Statistic)

}