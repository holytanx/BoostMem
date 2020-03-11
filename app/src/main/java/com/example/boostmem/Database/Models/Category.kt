package com.example.boostmem.Database.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "category_table")
data class Category(
    @PrimaryKey (autoGenerate = true)  var categoryID:Int = 0,
    @ColumnInfo (name="category_name") var categoryName : String )
