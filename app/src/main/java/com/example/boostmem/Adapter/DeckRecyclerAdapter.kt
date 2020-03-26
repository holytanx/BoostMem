package com.example.boostmem.Adapter

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.Database.Models.Category
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.ViewModel.CategoryViewModel
import com.example.boostmem.DeckViewModel
import com.example.boostmem.R
import kotlinx.android.synthetic.main.deck_view.view.*

class DeckRecyclerAdapter internal constructor(context:Context,application: Application,var clickListener: OnDeckItemClickListener) : RecyclerView.Adapter<DeckRecyclerAdapter.DeckViewHolder>()
,LifecycleOwner{
    private var context = context as (AppCompatActivity)
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var decks = emptyList<Deck>()
    private var viewModel = DeckViewModel(application)
    private var cateObject = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val itemView = inflater.inflate(R.layout.deck_view, parent, false)
        return DeckViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {

        val current = decks[position]
        val categoryType = decks[position].cateID
        val check = viewModel.allCates.observe(context, Observer {
            cateObject = it.filter { it.categoryID.equals(categoryType)}.firstOrNull()!!.categoryName
            Log.d("check",cateObject)
            holder.initialize(current,cateObject,this.clickListener)

        })

    }

    override fun getItemCount() = decks.size



    fun getDeckAtPosition(position: Int): Deck {
        return decks[position]
    }
    internal fun setDecks(decks: List<Deck>) {
        this.decks = decks
        notifyDataSetChanged()
    }

    inner class DeckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.deckTitle
        var category = itemView.deckCategory
        var button = itemView.reviewButton
        fun initialize(item :Deck,cateName:String?, action:OnDeckItemClickListener){
            title.text = item.deckName
            category.text = "Category: $cateName"

            itemView.setOnClickListener {
                action.onItemClick(item,adapterPosition)
            }
            button.setOnClickListener {
                action.onGameClick(item,adapterPosition)
            }
        }

    }

    override fun getLifecycle(): Lifecycle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

interface OnDeckItemClickListener{
    fun onItemClick(item:Deck, position: Int)
    fun onGameClick(item:Deck,position: Int)
}
