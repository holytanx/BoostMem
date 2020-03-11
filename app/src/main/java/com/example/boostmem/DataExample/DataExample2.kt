package com.example.boostmem.DataExample

import com.example.boostmem.Database.Models.Game

class DataExample2 {

    companion object {
        fun createMultiGames(): ArrayList<Game> {
            val multiGames = ArrayList<Game>()

            multiGames.add(
                Game(4,"YenCha Game","เกมเย็นชา")
            )
            return multiGames
        }
    }
}