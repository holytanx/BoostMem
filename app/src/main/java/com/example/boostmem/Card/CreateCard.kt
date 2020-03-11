package com.example.boostmem.Card

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.DeckViewModel
import com.example.boostmem.R
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_create_card.*
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.util.*


class CreateCard : AppCompatActivity() {
    lateinit var deckViewModel: DeckViewModel
    var defaultColor: Int = 0
    lateinit var deck : Deck
    lateinit var card : Card
    lateinit var mCardView: CardView
    lateinit var items : MutableList<Deck>
    lateinit var spinner: Spinner
    lateinit var status: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        initializedVars()
        var arrayAdapter: ArrayAdapter<String>
        if(intent.extras!!.get(CardManagement.ADDCARD) != null){
            deck = intent.extras?.get(CardManagement.ADDCARD) as Deck
            status = CardManagement.ADDCARD
        }
        if(intent.extras!!.get(CardManagement.EDITCARD)!= null){
            card = intent.extras?.get(CardManagement.EDITCARD) as Card
            mCardView.setBackgroundColor(card.color)
            deckViewModel.allDecks.observe(this, Observer {
                val value = it.filter { it.deckID == card.ownerID }.first()
                deck = Deck(value.deckID,value.deckName,value.cateID,value.description)
                front_EditText.setText(card.frontDesp)
                back_EditText.setText(card.backDesp)
                status = CardManagement.EDITCARD

            })
            }

        deckViewModel.allDecks.observe(this, Observer {
            items = it.toMutableList()
            Log.i("deckFromList:",items.size.toString())
            arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,items.map { it.deckName })
            spinner.adapter = arrayAdapter
            arrayAdapter.notifyDataSetChanged()
            if(status == CardManagement.ADDCARD) {
                if (deck.deckName.isNotEmpty()) {
                    val selectedPosition = arrayAdapter.getPosition(deck.deckName)
                    spinner.setSelection(selectedPosition)
                }
            }else if(status == CardManagement.EDITCARD){
                val selectedPosition = arrayAdapter.getPosition(deck.deckName)
                spinner.setSelection(selectedPosition)
                spinner.isEnabled = false
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
                deck = items[position]

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

        addColor.setOnClickListener {
            openColorPicker(mCardView)
        }

        addCard_button.setOnClickListener {
            if(status == CardManagement.ADDCARD){
                val front = front_EditText.text.toString()
                val back = back_EditText.text.toString()
                var card = Card(0,deck.deckID,front,back,defaultColor)
                deckViewModel.insert(card)
                Log.d("card","ID: ${card.cardID} deckID: ${card.ownerID} color: ${defaultColor}")
                Toast.makeText(this,"Successfully added a card",Toast.LENGTH_LONG).show()
            }else if(status == CardManagement.EDITCARD){
                val front = front_EditText.text.toString()
                val back = back_EditText.text.toString()
                deckViewModel.updateCard(Card(card.cardID,card.ownerID,front,back,defaultColor))
                Toast.makeText(this,"Successfully updated a card",Toast.LENGTH_LONG).show()

            }

        }




    }

    fun openColorPicker(cardView: CardView) {
        val colorPicker =
            AmbilWarnaDialog(this, defaultColor, object : OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog) {}
                @SuppressLint("ResourceType")
                override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                    defaultColor = color
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        cardView.setBackgroundColor(defaultColor)
                    }
                }
            })
        colorPicker.show()
    }

    fun initializedVars(){
        mCardView= findViewById<CardView>(R.id.materialCardView)
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        items = mutableListOf<Deck>()
        spinner = findViewById<Spinner>(R.id.deck_Spinner)
    }

}
