package com.example.boostmem.Games

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager.widget.ViewPager
import com.example.boostmem.Adapter.GamesPagerAdapter
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.Game
import com.example.boostmem.MainActivity
import com.example.boostmem.R
import me.relex.circleindicator.CircleIndicator

class Games : AppCompatActivity() {
    lateinit var deck: Deck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

         deck = intent.extras?.get(MainActivity.PLAYGAME) as Deck


    }
}
