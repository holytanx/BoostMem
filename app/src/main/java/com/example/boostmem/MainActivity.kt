package com.example.boostmem

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.boostmem.Adapter.DeckRecyclerAdapter
import com.example.boostmem.Adapter.GamesPagerAdapter
import com.example.boostmem.Adapter.OnDeckItemClickListener
import com.example.boostmem.Card.CardManagement
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.Statistic
import com.example.boostmem.Deck.CreateDeck
import com.example.boostmem.Games.Games
import com.example.boostmem.Notification.Notifications
import com.example.boostmem.Notification.NotificationBroadcast
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.relex.circleindicator.CircleIndicator
import org.jetbrains.anko.layoutInflater
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity(), OnDeckItemClickListener {
    private lateinit var recyclerView : RecyclerView
    private val newDeckActivityRequestCode = 1
    private lateinit var deckViewModel : DeckViewModel
    private lateinit var deckAdapter : DeckRecyclerAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNav : BottomNavigationView = findViewById(R.id.bottomNav)

        //declare recyclerview
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        deckAdapter = DeckRecyclerAdapter(this@MainActivity,application,this)
        recyclerView.adapter = deckAdapter

        deckViewModel = DeckViewModel(application)
        deckViewModel.allDecks.observe(this, Observer { decks ->
            decks?.let { deckAdapter.setDecks(it) }
        })


        var today = LocalDateTime.now()
        val calNow = Calendar.getInstance()


        Log.d("today",today.dayOfWeek.toString().toLowerCase())



        //Bottom navigation
            bottomNav.selectedItemId = R.id.Home

            bottomNav.setOnNavigationItemSelectedListener {

                when(it.itemId){
                    R.id.Home -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        overridePendingTransition(0,0)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.statistics -> {
                        startActivity(Intent(this, Statistics::class.java))
                        overridePendingTransition(0,0)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.notification -> {
                        startActivity(Intent(this, Notifications::class.java))
                        overridePendingTransition(0,0)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.settings -> {
                        startActivity(Intent(this, Settings::class.java))
                        overridePendingTransition(0,0)
                        return@setOnNavigationItemSelectedListener true
                    }


                    else -> false
                }

            }

        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                    target: androidx.recyclerview.widget.RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.adapterPosition
                    val deck = deckAdapter.getDeckAtPosition(position)

                    if (direction == ItemTouchHelper.LEFT) {
                        deckViewModel.deleteDeck(deck)
                        deckViewModel.allDecks
                        Toast.makeText(applicationContext,"Delete deck: ${deck.deckName} (${deck.deckID})",Toast.LENGTH_LONG).show()

                    }
                }
            })
        helper.attachToRecyclerView(recyclerView)
    }



//    fun selectGames(view: View){
//        val intent = Intent(this, Games::class.java)
//
//        startActivity(intent)
//    }

    fun createnewDeck(view: View){
        val intent = Intent(this, CreateDeck::class.java)
        startActivityForResult(intent, newDeckActivityRequestCode )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newDeckActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->

                    val list = data.getSerializableExtra(CreateDeck.EXTRA_REPLY) as? Deck
                    Toast.makeText(applicationContext, list.toString(),Toast.LENGTH_LONG).show()
                    deckViewModel.insert(list!!)
//                    deckViewModel.insert(statistic = Statistic(0,list.deckID,0f,0))
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Nothing",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onItemClick(item: Deck, position: Int) {
        val intent = Intent(applicationContext,CardManagement::class.java)
        intent.putExtra(MANAGEMENT, item )
        startActivity(intent)
//        Toast.makeText(this,"Click me",Toast.LENGTH_LONG).show()

    }
    companion object {
        const val MANAGEMENT = "com.example.boostmem.MANAGEMENT"
        const val PLAYGAME = "com.example.boostmem.Games"
    }


    override fun onGameClick(item: Deck, position: Int) {
//        Toast.makeText(this,"You clicked : ${item.deckName}",Toast.LENGTH_LONG).show()
        val intent = Intent(applicationContext,Games::class.java)
        intent.putExtra(PLAYGAME,item)
        startActivity(intent)
    }



}


