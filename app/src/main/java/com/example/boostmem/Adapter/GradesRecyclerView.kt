package com.example.boostmem.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.R
import com.example.boostmem.Database.Models.Statistic
import kotlinx.android.synthetic.main.grade_view.view.*


class GradesRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var grades: ArrayList<Statistic> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return GradeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grade_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is GradeViewHolder -> {
                holder.bind(grades.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return grades.size
    }


    fun submitList(gradeSet: List<Statistic>) {
        grades = gradeSet as ArrayList<Statistic>
    }

    class GradeViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.deckGrade_textView
        val grade = itemView.grade_textView

        fun bind(statistic: Statistic) {
            title.text = statistic.deckownerID.toString()
            grade.text = "50% C"
        }
    }
}

