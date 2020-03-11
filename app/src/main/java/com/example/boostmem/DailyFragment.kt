package com.example.boostmem

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boostmem.Adapter.NotificationRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_daily.*


class DailyFragment : Fragment() {
    private lateinit var notiAdapter: NotificationRecyclerAdapter


    companion object {
        fun newInstance() = DailyFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false)

    }


}
