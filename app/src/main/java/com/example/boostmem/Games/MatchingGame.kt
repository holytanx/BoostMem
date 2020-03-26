package com.example.boostmem.Games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boostmem.Adapter.MatchingGameRecyclerAdapter
import com.example.boostmem.R
import kotlinx.android.synthetic.main.activity_matching_game.*

class MatchingGame : AppCompatActivity() {

    lateinit var matchingFrontAdapter: MatchingGameRecyclerAdapter
    lateinit var matchingBackAdapter: MatchingGameRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_game)

//        initFrontRecyclerView()
//        initBackRecyclerView()

//        var arrayList = ArrayList<String>(3)
//        arrayList.add("cat")
//        arrayList.add("dog")
//        arrayList.add("rat")
//        matchingFrontAdapter.submitList(arrayList)
//
//        var arrayList1 = ArrayList<String>(3)
//        arrayList1.add("any member of the group of animals similar to the cat, such as the lion:")
//        arrayList1.add("a common animal with four legs, especially kept by people as a pet or to hunt or guard things:")
//        arrayList1.add("a small rodent, larger than a mouse, that has a long tail and is considered to be harmful:")
//        matchingBackAdapter.submitList(arrayList1)
    }

//
//    private fun initFrontRecyclerView(){
//        front_card.apply {
//            layoutManager = LinearLayoutManager(this@MatchingGame)
//            matchingFrontAdapter = MatchingGameRecyclerAdapter(this)
//            adapter = matchingFrontAdapter
//        }
//    }
//    private fun initBackRecyclerView(){
//        back_card.apply {
//            layoutManager = LinearLayoutManager(this@MatchingGame)
//            matchingBackAdapter = MatchingGameRecyclerAdapter(this)
//            adapter = matchingBackAdapter
//        }
//    }

}
