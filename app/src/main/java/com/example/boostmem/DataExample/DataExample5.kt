package com.example.boostmem.DataExample

import com.example.boostmem.Database.Models.Statistic

class DataExample5 {
    companion object {
        fun createStatistic(): ArrayList<Statistic> {
            val statistics = ArrayList<Statistic>()

           statistics.add(Statistic(1,1,80f,1))
            statistics.add(Statistic(2,2,80f,2))

            return statistics
        }
    }
}