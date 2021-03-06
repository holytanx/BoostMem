package com.example.boostmem.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.R
import kotlinx.android.synthetic.main.matchgame_view.view.*
import java.util.*
import kotlin.collections.ArrayList


class MatchingGameRecyclerAdapter( var clickListener: OnCardItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var cards : ArrayList<Card> = ArrayList()
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
            is MatchingGameViewHolder -> (holder.bind(cards.get(position),this.clickListener))
        }

    }
    fun submitList(cardset: ArrayList<Card>){
        cards = cardset
    }

    class MatchingGameViewHolder constructor(
        itemView: View
    ):RecyclerView.ViewHolder(itemView){

        var textview = itemView.cardFrontorBack

        fun bind (string: Card,action:OnCardItemClickListener){
            textview.text = string.frontDesp
            val rnd = Random()
            val currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            itemView.setBackgroundColor(currentColor)
            itemView.setOnClickListener{
                action.onItemClick(string,adapterPosition)
            }
        }

    }
    interface  OnCardItemClickListener{
        fun onItemClick(item:Card, position: Int)

    }
}