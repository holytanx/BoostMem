package com.example.boostmem.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.R
import kotlinx.android.synthetic.main.matchgame_view.view.*


class MatchingGameRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var cards : ArrayList<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MatchingGameViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.matchgame_view,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MatchingGameViewHolder -> (holder.bind(cards.get(position)))
        }

    }
    fun submitList(cardset : ArrayList<String>){
        cards = cardset
    }

    class MatchingGameViewHolder constructor(
        itemView: View
    ):RecyclerView.ViewHolder(itemView){

        var textview = itemView.cardFrontorBack

        fun bind (string : String){
            textview.text = string
        }

    }

}