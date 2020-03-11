package com.example.boostmem

import android.app.Activity
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.Adapter.DeckRecyclerAdapter
import com.example.boostmem.Adapter.OnDeckItemClickListener
import com.example.boostmem.Card.CardManagement
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Deck.CreateDeck
import com.example.boostmem.Games.Games
import com.example.boostmem.Notification.Notifications
import com.example.boostmem.Notification.NotificationBroadcast
import com.google.android.material.bottomnavigation.BottomNavigationView
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

//        if(calSet.compareTo(calNow) > 0){
//            println("Hello")
//        }
        Log.d("today",today.dayOfWeek.toString().toLowerCase())

//        deckViewModel.allNoti.observe(this, Observer {
//            var notifications = it.filter { it.dayList!!.contains(today.dayOfWeek.toString().toLowerCase()) }
//            Log.d("today","${notifications.size}")
//            for (noti in notifications){
//                val calSet = calNow.clone() as Calendar
//                val hr = noti.time.split(":")[0]
//                val m = noti.time.split(":")[1]
//                val hr_white: String = hr.trim()
//                val m_white: String = m.trim()
//                calSet.set(Calendar.HOUR_OF_DAY,hr_white.toInt())
//                calSet.set(Calendar.MINUTE,m_white.toInt())
//                if(calSet.compareTo(calNow) > 0){
//                    getNotification(noti.deckName)?.let { it1 -> scheduleNotification(it1, (calSet.timeInMillis - calNow.timeInMillis)) }
//                    Log.d("alarm","${noti.notificationID} , ${noti.time}")
//                }else{
//                    Log.d("can'talarm","${noti.notificationID}")
//
//                }
//            }
//        })

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



    fun selectGames(view: View){
        val intent = Intent(this, Games::class.java)
        startActivity(intent)
    }

    fun createnewDeck(view: View){
        val intent = Intent(this, CreateDeck::class.java)
        startActivityForResult(intent, newDeckActivityRequestCode )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newDeckActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->

                    val deck = data.getSerializableExtra(CreateDeck.EXTRA_REPLY) as? Deck
                    Toast.makeText(applicationContext,deck!!.description,Toast.LENGTH_LONG).show()
                    deckViewModel.insert(deck)
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
    }





}


