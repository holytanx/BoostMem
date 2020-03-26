package com.example.boostmem

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.Statistic
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.fragment_overall.*
import java.util.*


class OverallFragment : Fragment() {
    lateinit var deckViewModel: DeckViewModel
     var list = mutableListOf<Statistic>()
    var deckName = mutableListOf<Deck>()

    val MY_COLORS = intArrayOf(
        Color.rgb(192, 0, 0),
        Color.rgb(146, 208, 80),
        Color.rgb(0, 176, 80),
        Color.rgb(79, 129, 189)
    )

    var MY_COLORS2 = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        for(n in 1..20){
            val ran = Random()
            MY_COLORS2.add(Color.argb(255, ran.nextInt(256), ran.nextInt(256), ran.nextInt(256)))
        }
        deckViewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)
        val y = deckViewModel.allDecks.observe(viewLifecycleOwner, Observer <List<Deck>>{
           deckName = it.toMutableList()
            val x = deckViewModel.allStatistic.observe(viewLifecycleOwner,Observer<List<Statistic>>{
                list = it.toMutableList()
                definePine(list,deckName)
            })
        })


        return inflater.inflate(R.layout.fragment_overall, container, false)

    }



    private fun definePine(list: MutableList<Statistic>,deckList: MutableList<Deck>) {
        var total = 0
        val entries: MutableList<PieEntry> = ArrayList<PieEntry>()
        for((index,values) in list.withIndex()){
          total+=values.playCount
//            entries.add(PieEntry(values.average,deckList.filter { it.deckID == values.deckownerID }.firstOrNull()!!.deckName))
        }
        for((index,values) in list.withIndex()) {
            val percent = (values.playCount*100.0f)/total
            entries.add(PieEntry(percent,deckList.filter { it.deckID == values.deckownerID }.firstOrNull()!!.deckName))
        }
        var colors = ArrayList<Int>()
        // add colors to list
        for (c in MY_COLORS2) colors.add(c)
        val set = PieDataSet(entries, "Most Reviewed games")
        val data = PieData(set)
        data.setValueFormatter(PercentFormatter(pieChart))
        set.setColors(colors)
        set.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        // configure pie chart
        pieChart.setUsePercentValues(true)
        pieChart.isDrawHoleEnabled = true
        pieChart.holeRadius = 7f
        pieChart.transparentCircleRadius = 10f
        pieChart.setEntryLabelColor(Color.WHITE);
        var legend : com.github.mikephil.charting.components.Legend= pieChart.legend
        legend.xEntrySpace = 7f
        legend.yEntrySpace = 5f
        pieChart.data = data
        pieChart.animateXY(1000,1000)
        pieChart.invalidate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //define color


        //define statistics

    }

}
