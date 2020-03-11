package com.example.boostmem

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.fragment_overall.*
import java.util.*
import com.github.mikephil.charting.components.Legend


class OverallFragment : Fragment() {
    val MY_COLORS = intArrayOf(
        Color.rgb(192, 0, 0),
        Color.rgb(146, 208, 80),
        Color.rgb(0, 176, 80),
        Color.rgb(79, 129, 189)
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overall, container, false)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //define color
        var colors = ArrayList<Int>()

        // add colors to list
        for (c in MY_COLORS) colors.add(c)

        //define statistics
        val entries: MutableList<PieEntry> = ArrayList<PieEntry>()
        entries.add(PieEntry(25f, "Linux Commands"))
        entries.add(PieEntry(15f, "Chemistry"))
        entries.add(PieEntry(20f, "History II"))
        entries.add(PieEntry(40f, "Vocabulary Eng Set 1"))



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
        pieChart.animateXY(2000,2000)
        pieChart.invalidate()

    }

}
