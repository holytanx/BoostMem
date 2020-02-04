package com.example.boostmem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boostmem.Adapter.GameRecyclerAdapter
import com.example.boostmem.DataExample.DataExample1
import kotlinx.android.synthetic.main.fragment_single.*
import java.util.*


class SingleFragment : Fragment() {
    private lateinit var gamesAdapter: GameRecyclerAdapter

    companion object {
        fun newInstance() = SingleFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance =true
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single, container, false)


    }


    private fun addData() {
        val data = DataExample1.createSingleGames()

        Log.i("Size",data.size.toString())
        gamesAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        single_recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            gamesAdapter = GameRecyclerAdapter()
            adapter = gamesAdapter
        }
    }





}

