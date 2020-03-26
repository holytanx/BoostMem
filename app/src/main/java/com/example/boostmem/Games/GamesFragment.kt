package com.example.boostmem.Games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.example.boostmem.Adapter.GamesPagerAdapter
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.DeckViewModel
import com.example.boostmem.R
import com.example.boostmem.databinding.FragmentGamesBinding


/**
 * A simple [Fragment] subclass.
 */
class GamesFragment : Fragment(){

    lateinit var viewPager:ViewPager
    lateinit var navController: NavController
    lateinit var deckViewModel: DeckViewModel
    lateinit var binding : FragmentGamesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate<FragmentGamesBinding>(inflater,
            R.layout.fragment_games,container,false)

        viewPager = binding.viewPager
        var circle = binding.circle
        val gamePagerAdapter = GamesPagerAdapter(childFragmentManager)
        gamePagerAdapter.addFragment(SingleFragment())
        gamePagerAdapter.addFragment(MultiFragment())
        viewPager.adapter = gamePagerAdapter
        circle.setViewPager(viewPager)
        gamePagerAdapter.registerDataSetObserver(circle.dataSetObserver)
        val deck = (activity as Games).deck
        viewPager.setPageTransformer(true, ViewPager.PageTransformer { page, position ->
            if (position < -1) {
            } else if (position < 1) {
                // Multiple
            } else {
                // Single
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


    }

}
