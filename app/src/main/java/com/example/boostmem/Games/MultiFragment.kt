package com.example.boostmem.Games

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.R
import com.example.boostmem.databinding.FragmentMultiBinding
import com.example.boostmem.databinding.FragmentSingleBinding


class MultiFragment : Fragment() {

    lateinit var binding:FragmentMultiBinding
    lateinit var deck: Deck


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate<FragmentMultiBinding>(inflater,R.layout.fragment_multi,container,false)
        deck = (activity as Games).deck

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.yenchaButton.setOnClickListener{
            findNavController().navigate(GamesFragmentDirections.actionGamesFragmentToYenchaGameFragment(deck))
        }

    }


}
