package com.example.boostmem.Card

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.*
import com.example.boostmem.Adapter.DeckRecyclerAdapter
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.R
import kotlinx.android.synthetic.main.activity_card_management.*
import kotlinx.android.synthetic.main.activity_card_management.view.*


class CardManagement : AppCompatActivity(),OnCardItemClickListener,LifecycleOwner {
    private lateinit var recyclerView : RecyclerView
    private lateinit var lifecycleRegistry: LifecycleRegistry

    lateinit var deckViewModel: DeckViewModel
    private lateinit var cardAdapter : CardRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_management)
        lifecycleRegistry = LifecycleRegistry(this)

        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        var item = intent.extras?.get(MainActivity.MANAGEMENT) as Deck
        var items = mutableListOf<Deck>()
        var spinner = findViewById<Spinner>(R.id.deck_spinner)
        var arrayAdapter: ArrayAdapter<String>
        recyclerView = findViewById(R.id.card_recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        cardAdapter = CardRecyclerAdapter(this@CardManagement,application,this)
        recyclerView.adapter = cardAdapter

        deckViewModel = DeckViewModel(application)
        deckViewModel.allCards.observe(this, Observer { decks ->
            decks?.let { cardAdapter.setCards(it.filter { it.ownerID.equals(item.deckID) }) }
        })
        deckViewModel.allDecks.observe(this, Observer {
            items = it.toMutableList()
            Log.i("deckFromList:",items.size.toString())
            arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,items.map { it.deckName })
            spinner.adapter = arrayAdapter
            arrayAdapter.notifyDataSetChanged()
            if(item.deckName.isNotEmpty()){
                val selectedPosition = arrayAdapter.getPosition(item.deckName)
                spinner.setSelection(selectedPosition)
            }
        })



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                descriptionDeck_EditText.setText(items[position].description)
                item = items[position]
                deckViewModel.allCards.observe(this@CardManagement, Observer { decks ->
                    decks?.let { cardAdapter.setCards(it.filter { it.ownerID.equals(item.deckID) }) }
                })

            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }



        }

//        Toast.makeText(this,item.deckID.toString(),Toast.LENGTH_LONG).show()
        addCard_button.setOnClickListener {
            val intent = Intent(this,CreateCard::class.java)
            intent.putExtra(ADDCARD, item )
            startActivity(intent)

        }


    }
    companion object {
        const val ADDCARD = "com.example.boostmem.ADDCARD"
        const val EDITCARD = "com.example.boostmem.EDITCARD"

    }

    override fun onItemClick(item: Card, position: Int) {
        Toast.makeText(applicationContext,item.cardID.toString(),Toast.LENGTH_LONG).show()
        val intent = Intent(applicationContext,CreateCard::class.java)
        intent.putExtra(EDITCARD, item )
        startActivity(intent)
    }




}
