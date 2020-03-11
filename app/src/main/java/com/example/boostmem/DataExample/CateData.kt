package com.example.boostmem.DataExample

import com.example.boostmem.Database.Models.Category
import com.example.boostmem.Database.Models.Game

class CateData{
    companion object {
        fun createCate(): ArrayList<Category> {
            val list = ArrayList<Category>()

            list.add(
                Category(1,"Education")
            )
            list.add(
                Category(2,"IT")
            )
            return list
        }
    }
}