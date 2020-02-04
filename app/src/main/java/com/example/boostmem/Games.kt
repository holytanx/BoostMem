package com.example.boostmem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.boostmem.Adapter.GameRecyclerAdapter
import com.example.boostmem.Adapter.GamesPagerAdapter

class Games : AppCompatActivity() {

    private lateinit var viewPager : ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        viewPager = findViewById(R.id.viewPager)
//
//        initRecyclerView()
//        addData()
        val gamePagerAdapter = GamesPagerAdapter(supportFragmentManager)
        gamePagerAdapter.addFragment(SingleFragment())
        gamePagerAdapter.addFragment(MultiFragment())
        viewPager.adapter = gamePagerAdapter



    }
//    private fun addData(){
//        val data = DataExample1.createSingleGames()
//        gamesAdapter.submitList(data)
//    }
//    private fun initRecyclerView(){
//        game_recyclerView.apply{
//            layoutManager = LinearLayoutManager(this@Games)
//            gamesAdapter = GameRecyclerAdapter()
//            adapter = gamesAdapter
//        }
//
//    }


}
