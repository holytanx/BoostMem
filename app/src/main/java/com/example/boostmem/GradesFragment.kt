package com.example.boostmem


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.boostmem.DataExample.DataExample5
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.Statistic
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_grades.*
import java.util.*
import kotlin.collections.ArrayList


class GradesFragment : Fragment() {
    lateinit var mChart: HorizontalBarChart
    lateinit var deckViewModel: DeckViewModel
    var MY_COLORS2 = mutableListOf<Int>()
    var list = mutableListOf<Statistic>()
    var deckName = mutableListOf<Deck>()

    val data = DataExample5.createStatistic()

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
                setGradeGraph(list,deckName)
            })
        })

        return inflater.inflate(R.layout.fragment_grades, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    private fun setGradeGraph(
        statistics: MutableList<Statistic>,
        deckName: MutableList<Deck>
    ) {
        mChart = grade_chart
        mChart.setDrawBarShadow(false)
        val description = Description()
        description.text = ""
        mChart.description = description
        mChart.legend.isEnabled = true
        mChart.setPinchZoom(false)
        mChart.setDrawValueAboveBar(false)

        val xAxis = mChart.getXAxis()
        xAxis.setDrawGridLines(false)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setEnabled(true)
        xAxis.setDrawAxisLine(false)

        val yLeft = mChart.axisLeft
        yLeft.axisMaximum = 100f
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        xAxis.setLabelCount(statistics.count())

        val entries = ArrayList<BarEntry>()
        val xAxisLabel: ArrayList<String> = ArrayList()
        for((index,values) in statistics.withIndex()){
            xAxisLabel.add(deckName[index.toInt()].deckName)
            entries.add(BarEntry(index.toFloat(),values.average))
        }


        mChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
        mChart.xAxis.position = XAxis.XAxisPosition.BOTTOM


        val yRight = mChart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        val barDataSet = BarDataSet(entries, getString(R.string.labelDeck))
        barDataSet.setColors(
            MY_COLORS2
        )
        mChart.setDrawBarShadow(true)
        barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
        val data = BarData(barDataSet)
        data.barWidth = 0.9f
        mChart.data = data
        mChart.invalidate()
        mChart.animateY(2000)


    }



}



