package com.example.boostmem.DataExample

import com.example.boostmem.Database.Models.Statistic

class DataExample5 {
    companion object {
        fun createStatistic(): ArrayList<Statistic> {
            val statistics = ArrayList<Statistic>()

           statistics.add(Statistic(1,1,5,6,7,8,9,10,22))
            statistics.add(Statistic(2,2,5,6,7,8,9,10,22))

            return statistics
        }
    }
}