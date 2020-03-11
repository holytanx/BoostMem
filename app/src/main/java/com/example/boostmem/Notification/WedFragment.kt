package com.example.boostmem.Notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.Adapter.NotificationRecyclerAdapter
import com.example.boostmem.Adapter.OnCheckSwitchChangeListener
import com.example.boostmem.Adapter.OnNotiItemClickListener
import com.example.boostmem.Database.Models.NotificationModel
import com.example.boostmem.Database.Repository.NotiRepository
import com.example.boostmem.DeckViewModel
import com.example.boostmem.R
import kotlinx.android.synthetic.main.fragment_tue.*


class WedFragment : Fragment(), OnNotiItemClickListener,OnCheckSwitchChangeListener {
    private lateinit var notiAdapter: NotificationRecyclerAdapter
    private lateinit var deckViewModel: DeckViewModel
    private var monList = mutableListOf<NotificationModel>()
    var notiID: Long = 0
    private var isCheck: Boolean = true
    var fragment = Fragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wed, container, false)
        deckViewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)
        val wedRecyclerView = view.findViewById<RecyclerView>(R.id.wedRecyclerView)
        wedRecyclerView.layoutManager = LinearLayoutManager(activity)

        notiAdapter = NotificationRecyclerAdapter(view.context, this@WedFragment, this@WedFragment)
        wedRecyclerView.adapter = notiAdapter
        notiAdapter.notifyDataSetChanged();

        deckViewModel.allNoti.observe(viewLifecycleOwner, Observer {
            notiAdapter.setNoti(it.filter { it.dayList!!.contains("wednesday") }.toMutableList())
            Log.d("time", it.toString())
        }
        )

        LocalBroadcastManager.getInstance(this!!.context!!).registerReceiver(
            mMessageReceiver,
            IntentFilter("edit")
        )
        LocalBroadcastManager.getInstance(this!!.context!!).registerReceiver(
            mDeleteReceiver,
            IntentFilter("delete")
        )


        return view


    }


    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            isCheck = true
        } else {
            isCheck = false
        }


    }

    override fun position(notiID: Long) {
        Toast.makeText(context, " $notiID $isCheck", Toast.LENGTH_LONG).show()
        updateToDB(notiID, isCheck)
    }

    private fun updateToDB(id: Long, check: Boolean) {
        deckViewModel.updateIsActiveNoti(myTaskParams = NotiRepository.MyTaskParams(check, id))
    }


    override fun onItemClick(item: NotificationModel, position: Int, act: String) {
        if (act == "delete") {

        } else {
            Toast.makeText(context, "Show me", Toast.LENGTH_LONG).show()
        }
    }

    override fun position(notiID: Long, act: String) {
        Toast.makeText(context, "$notiID : $act", Toast.LENGTH_LONG).show()
    }

    var mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context?,
            intent: Intent
        ) { // Get extra data included in the Intent
            val ItemName = intent.extras!!.get("editNoti")
            if (ItemName != null) {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()

            }
        }
    }
    var mDeleteReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context?,
            intent: Intent
        ) { // Get extra data included in the Intent
            val ItemName = intent.extras!!.get("deleteNoti") as NotificationModel
            if (ItemName != null) {
                deckViewModel.deleteNoti(ItemName)
            }
        }

    }
}
