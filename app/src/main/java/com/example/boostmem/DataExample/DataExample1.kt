package com.example.boostmem.DataExample

import com.example.boostmem.Database.Models.Game
class DataExample1 {

    companion object {
        fun createSingleGames(): ArrayList<Game> {
            val singleGames = ArrayList<Game>()

            singleGames.add(
                Game(1,"Basic Review","ทบทวนพื้นฐาน")
            )
            singleGames.add(
                Game(2,"Matching Game","เกมจับคู่")
            )
            singleGames.add(
                Game(3,"Quiz","เกมปรนัย")
            )
            return singleGames
        }
    }
}