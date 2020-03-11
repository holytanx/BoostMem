package com.example.boostmem.Games

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.boostmem.R
import kotlinx.android.synthetic.main.fragment_single.view.*


class SingleFragment : Fragment() {

    companion object {
        fun newInstance() = SingleFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance =true
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initRecyclerView()
//        addData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater!!.inflate(R.layout.fragment_single, container, false)

        lateinit var intent : Intent

        view.basicReview_button.setOnClickListener{view ->
            intent = Intent(activity, BasicReview::class.java)
            startActivity(intent)
        }
        view.quiz_button.setOnClickListener { view ->
            intent = Intent(activity, Quiz::class.java)
            startActivity(intent)
        }
        view.match_button.setOnClickListener { view ->
            intent = Intent(activity, MatchingGame::class.java)
            startActivity(intent)
        }

        return view


    }



//    private fun addData() {
//        val data = DataExample1.createSingleGames()
//
//        Log.i("Size",data.size.toString())
//        gamesAdapter.submitList(data)
//    }

//    private fun initRecyclerView() {
//        single_recyclerView.apply {
//            layoutManager = LinearLayoutManager(activity)
//            gamesAdapter = GameRecyclerAdapter()
//            adapter = gamesAdapter
//        }
//    }





}

