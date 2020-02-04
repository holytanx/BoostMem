package com.example.boostmem

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boostmem.Adapter.GameRecyclerAdapter
import com.example.boostmem.DataExample.DataExample2
import kotlinx.android.synthetic.main.fragment_multi.*


class MultiFragment : Fragment() {
    private lateinit var gamesAdapter: GameRecyclerAdapter

    companion object{
        fun newInstance() = MultiFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_multi,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addData()
    }

    private fun addData() {
        val data = DataExample2.createMultiGames()
        Log.i("Size",data.size.toString())
        gamesAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        multi_recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            gamesAdapter = GameRecyclerAdapter()
            adapter = gamesAdapter
        }
    }
}
