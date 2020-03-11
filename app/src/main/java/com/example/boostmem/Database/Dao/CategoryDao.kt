package com.example.boostmem.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.boostmem.Database.Models.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cate: Category)

    @Query("DELETE FROM category_table")
    fun deleteAll()

    @Query("SELECT * from category_table ORDER BY categoryID ASC")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT COUNT(category_name) FROM category_table")
    fun getDataCount(): Int


    @Delete
    fun deleteCate(cate: Category?)
}