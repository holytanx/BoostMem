package com.example.boostmem.DataExample

import com.example.boostmem.models.Game
class DataExample1 {

    companion object {
        fun createSingleGames(): ArrayList<Game> {
            val singleGames = ArrayList<Game>()

            singleGames.add(
                Game("Basic Review","ทบทวนพื้นฐาน")
            )
            singleGames.add(
                Game("Audio Review","ทบทวนฟังเสียง")
            )
            singleGames.add(
                Game("Matching Game","เกมจับคู่")
            )
            singleGames.add(
                Game("Select Options","เกมปรนัย")
            )
            return singleGames
        }
    }
}