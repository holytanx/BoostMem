package com.example.boostmem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boostmem.Adapter.DeckRecyclerAdapter
import com.example.boostmem.DataExample.DataExample
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var deckAdapter : DeckRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottomNav)

        initRecyclerView()
        addData()
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
    }
    private fun addData(){
        val data = DataExample.createDeckSet()
        deckAdapter.submitList(data)
    }
    private fun initRecyclerView(){
        recyclerView.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            deckAdapter = DeckRecyclerAdapter()
            adapter = deckAdapter
        }

    }

    fun selectGames(view: View){
        val intent = Intent(this, Games::class.java)
        startActivity(intent)
    }
}
