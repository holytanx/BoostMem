package com.example.boostmem.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.R
import com.example.boostmem.models.Game
import kotlinx.android.synthetic.main.game_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class GameRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var games: ArrayList<Game> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return GameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GameViewHolder -> { holder.bind(games[position]) }
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }
    fun submitList(gameSet: ArrayList<Game>) {
        games = gameSet
    }

    class GameViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.gameName

        fun bind(game: Game) {
            val isLangTH: Boolean = Locale.getDefault().getLanguage().equals("th")
            if(isLangTH){
                title.setText(game.gameTH)
            }else{
                title.setText(game.gameENG)

            }
        }
    }
}