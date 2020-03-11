package com.example.boostmem.Games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.boostmem.Adapter.GamesPagerAdapter
import com.example.boostmem.R
import me.relex.circleindicator.CircleIndicator

class Games : AppCompatActivity() {

    private lateinit var viewPager : ViewPager
    private lateinit var circle : CircleIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        viewPager = findViewById(R.id.viewPager)
        circle = findViewById(R.id.circle)
//        initRecyclerView()
//        addData()
        val gamePagerAdapter = GamesPagerAdapter(supportFragmentManager)
        gamePagerAdapter.addFragment(SingleFragment())
        gamePagerAdapter.addFragment(MultiFragment())
        viewPager.adapter = gamePagerAdapter
        circle.setViewPager(viewPager)

        gamePagerAdapter.registerDataSetObserver(circle.dataSetObserver)


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
