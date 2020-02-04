package com.example.boostmem.DataExample

import com.example.boostmem.models.Deck
import com.example.boostmem.models.Game

class DataExample{

    companion object{
        fun createDeckSet(): ArrayList<Deck>{
            val list = ArrayList<Deck>()
            list.add(
                Deck("Vocabulary Eng Set 1","Education")
            )
            list.add(
                Deck("Linux Commands","IT")
            )
            list.add(
                Deck("Chemistry","Education")
            )
            list.add(
                Deck("History II","Education")
            )
            return list
        }
    }


}