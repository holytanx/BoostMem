package com.example.boostmem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.boostmem.Adapter.NotificationRecyclerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_notifications.*

class Notifications : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)



        tab_daily!!.addTab(tab_daily!!.newTab().setText(R.string.daily))
        tab_daily!!.addTab(tab_daily!!.newTab().setText(R.string.mon))
        tab_daily!!.addTab(tab_daily!!.newTab().setText(R.string.tue))
        tab_daily!!.addTab(tab_daily!!.newTab().setText(R.string.wed))
        tab_daily!!.addTab(tab_daily!!.newTab().setText(R.string.thu))
        tab_daily!!.addTab(tab_daily!!.newTab().setText(R.string.fri))
        tab_daily!!.addTab(tab_daily!!.newTab().setText(R.string.sat))
        tab_daily!!.addTab(tab_daily!!.newTab().setText(R.string.sun))
        tab_daily!!.tabGravity = TabLayout.GRAVITY_FILL


        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.notification

        bottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.Home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.statistics -> {
                    startActivity(Intent(this, Statistics::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notification -> {
                    startActivity(Intent(this, Notifications::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.settings -> {
                    startActivity(Intent(this, Settings::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }


                else -> false
            }
        }
    }


}
