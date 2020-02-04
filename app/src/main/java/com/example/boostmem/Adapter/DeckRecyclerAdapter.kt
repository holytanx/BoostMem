package com.example.boostmem.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.R
import com.example.boostmem.models.Deck
import kotlinx.android.synthetic.main.deck_view.view.*

class DeckRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var decks : ArrayList<Deck> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return DeckViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.deck_view,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            when(holder){
                is DeckViewHolder -> {holder.bind(decks.get(position))}
            }
    }

    override fun getItemCount(): Int {
        return decks.size
    }


    fun submitList(deckSet: List<Deck>){
        decks = deckSet as ArrayList<Deck>
    }
    class DeckViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val title = itemView.deckTitle
        val category = itemView.deckCategory

        fun bind (deck : Deck){
            title.setText(deck.deckTitle)
            category.append(" "+deck.deckCategory)

        }
    }

}