package com.example.boostmem


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boostmem.Adapter.GradesRecyclerView
import com.example.boostmem.DataExample.DataExample5
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_grades.*


class GradesFragment : Fragment() {
    lateinit var gradeAdapter : GradesRecyclerView
    lateinit var mChart: HorizontalBarChart
    val data = DataExample5.createStatistic()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grades, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        setGradeGraph()

//        initRecyclerView()
//        addData()


    }

    private fun setGradeGraph() {
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

        xAxis.setLabelCount(4)

        val xAxisLabel: ArrayList<String> = ArrayList()
        xAxisLabel.add("Linux Commands")
        xAxisLabel.add("Chemistry")
        xAxisLabel.add("History II")
        xAxisLabel.add("Vocabulary Eng Set 1")



        mChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
        mChart.xAxis.position = XAxis.XAxisPosition.BOTTOM


        val yRight = mChart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        setData(data.size,100)
        mChart.animateY(2000)


    }


    private fun setData(size: Int, i: Int) {

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 27f))
        entries.add(BarEntry(1f, 45f))
        entries.add(BarEntry(2f, 65f))
        entries.add(BarEntry(3f, 77f))

        val barDataSet = BarDataSet(entries, "Grades")
        barDataSet.setColors(
//            ContextCompat.getColor(mChart.context, R.color.redButton),
//            ContextCompat.getColor(mChart.context, R.color.green),
//            ContextCompat.getColor(mChart.context, R.color.blueButton),
//            ContextCompat.getColor(mChart.context, R.color.yellowButton)
            Color.rgb(192, 0, 0),
            Color.rgb(146, 208, 80),
            Color.rgb(0, 176, 80),
            Color.rgb(79, 129, 189)
        )
        mChart.setDrawBarShadow(true)
        barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
        val data = BarData(barDataSet)
        data.barWidth = 0.9f
        mChart.data = data
        mChart.invalidate()


    }

//    private fun addData() {
//        val data = DataExample5.createStatistic()
//
//        Log.i("Size",data.size.toString())
//        gradeAdapter.submitList(data)
//    }

//    private fun initRecyclerView() {
//        grades_recyclerView.apply {
//            layoutManager = LinearLayoutManager(activity)
//            gradeAdapter = GradesRecyclerView()
//            adapter = gradeAdapter
//        }
//    }


}



