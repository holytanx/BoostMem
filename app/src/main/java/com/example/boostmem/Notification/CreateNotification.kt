package com.example.boostmem.Notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.Adapter.NotificationRecyclerAdapter
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.NotificationModel
import com.example.boostmem.DeckViewModel
import com.example.boostmem.R
import kotlinx.android.synthetic.main.activity_create_notification.*
import java.text.SimpleDateFormat
import java.util.*


class CreateNotification : AppCompatActivity() {
    var timeFormat = SimpleDateFormat("HH:mm ", Locale.US)
    var time : String =""
    var isClick : Boolean = false
    val now = Calendar.getInstance()
    var days  = ArrayList<String>()
    private lateinit var notiAdapter: NotificationRecyclerAdapter
    lateinit var deckViewModel: DeckViewModel
    lateinit var spinner: Spinner
    lateinit var deckset : MutableList<Deck>
    var deckID : Long = 0
    var deckName =""
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_notification)
        var arrayAdapter: ArrayAdapter<String>

        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        spinner = findViewById(R.id.deck_time_Spinner)
        hr_textview.setText(now.get(Calendar.HOUR_OF_DAY).toString())
        min_textview.setText(now.get(Calendar.MINUTE).toString())

        deckViewModel.allDecks.observe(this, Observer {
            deckset = it.toMutableList()
            Log.d("Hello",deckset.size.toString())
            arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,deckset.map { it.deckName })
            spinner.adapter = arrayAdapter
            arrayAdapter.notifyDataSetChanged()
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                deckName = deckset[position].deckName

            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        val listener: View.OnClickListener = View.OnClickListener {
            isClick = true
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                var hr : String = ""
                var m : String = ""
                if(hourOfDay < 10 && minute < 10){
                    hr = "0${hourOfDay}"
                    m = "0${minute}"
                }else if(hourOfDay>=10 && minute < 10){
                    hr = hourOfDay.toString()
                    m = "0${minute}"
                }
                else if (hourOfDay < 10 && minute >= 10){
                    hr = "0${hourOfDay}"
                    m = minute.toString()
                }else if(hourOfDay >= 10 && minute >= 10){
                    hr = hourOfDay.toString()
                    m = minute.toString()
                }
                hr_textview.text = hr
                min_textview.text = m
                time = (timeFormat.format(selectedTime.time).toString())


            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true
            )
            timePicker.show()
        }

        settime_button.setOnClickListener(listener)

        addNotification_button.setOnClickListener {
            if(time != ""){
                insertToDB()

//                NotificationHelper.scheduleNotification(this,0,1000*10,"Hello","World")
            }else{
                Toast.makeText(this,"Please set time",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun insertToDB() {
        deckViewModel.insert(NotificationModel(0,deckName,true,time,days ))
        Toast.makeText(applicationContext,"successfully added a notification",Toast.LENGTH_LONG).show()
        gotoNotificationMain()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel(){
        var name = "BoostmemChanel"
        val description = "Chanel"
        var importance = NotificationManager.IMPORTANCE_HIGH
        var chanel = NotificationChannel("notifiy",name,importance)
        chanel.description = description
        var notificationManager:NotificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(chanel)
    }
    fun changeColor(view: View) {
        view.setSelected(!view.isSelected());
        if(view.isSelected){
           when(view.id) {
               R.id.mon -> days.add("monday")
               R.id.tue -> days.add("tuesday")
               R.id.wed -> days.add("wednesday")
               R.id.thu -> days.add("thursday")
               R.id.fri -> days.add("friday")
               R.id.sat -> days.add("saturday")
               R.id.sun -> days.add("sunday")
           }
        }else{
            when(view.id) {
                R.id.mon -> days.remove("monday")
                R.id.tue -> days.remove("tuesday")
                R.id.wed -> days.remove("wednesday")
                R.id.thu -> days.remove("thursday")
                R.id.fri -> days.remove("friday")
                R.id.sat -> days.remove("saturday")
                R.id.sun -> days.remove("sunday")
            }
        }

        Log.d("days",days.size.toString())

    }

    fun gotoNotificationMain(){
        val intent = Intent(this,Notifications::class.java)
        startActivity(intent)
    }





}
