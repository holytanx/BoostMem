package com.example.boostmem.DataExample

import com.example.boostmem.models.Game

class DataExample2 {

    companion object {
        fun createMultiGames(): ArrayList<Game> {
            val multiGames = ArrayList<Game>()

            multiGames.add(
                Game("YenCha Game","เกมเย็นชา")
            )
            return multiGames
        }
    }
}