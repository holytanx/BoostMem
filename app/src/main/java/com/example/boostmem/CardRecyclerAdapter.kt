package com.example.boostmem

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.Database.Models.Card
import kotlinx.android.synthetic.main.card_management_view.view.*
import kotlinx.android.synthetic.main.deck_view.view.*

class CardRecyclerAdapter internal constructor(context: Context, application: Application, var clickListener: OnCardItemClickListener) : RecyclerView.Adapter<CardRecyclerAdapter.CardViewHolder>()
{
    private var context = context as (AppCompatActivity)
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cards = emptyList<Card>()
    private var viewModel = DeckViewModel(application)
    private var cardObject = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = inflater.inflate(R.layout.card_management_view, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val current = cards[position]
        holder.initialize(current,this.clickListener)
    }

    override fun getItemCount() = cards.size

    internal fun setCards(cards: List<Card>) {
        this.cards = cards
        notifyDataSetChanged()
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var front = itemView.front_Desp
        var back = itemView.back_Desp
        var bg = itemView.card_layout
        fun initialize(item : Card, action:OnCardItemClickListener){
            front.text = item.frontDesp
            back.text = item.backDesp
            bg.setBackgroundColor(item.color)
            itemView.setOnClickListener {
                action.onItemClick(item,adapterPosition)
            }
        }

    }


}

interface OnCardItemClickListener{
    fun onItemClick(item: Card, position: Int)

}
