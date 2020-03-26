package com.example.boostmem.Games

import android.R.attr.key
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.R
import com.example.boostmem.databinding.FragmentSingleBinding
import kotlin.properties.Delegates


class SingleFragment : Fragment(), View.OnClickListener{

    lateinit var navController: NavController
    lateinit var binding : FragmentSingleBinding
    lateinit var deck: Deck
    var num = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate<FragmentSingleBinding>(inflater,R.layout.fragment_single,container,false)
         deck = (activity as Games).deck


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.basicReviewButton.setOnClickListener(this)
        binding.matchButton.setOnClickListener(this)
        binding.quizButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!){
            binding.basicReviewButton -> navController.navigate(GamesFragmentDirections.actionGamesFragmentToBasicFragment(deck))
            binding.matchButton -> navController.navigate(GamesFragmentDirections.actionGamesFragmentToMatchingGameFragment(deck))
            binding.quizButton -> navController.navigate(GamesFragmentDirections.actionGamesFragmentToQuizFragment(deck))
        }
    }




}

