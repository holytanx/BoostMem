package com.example.boostmem.Games

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.boostmem.MainActivity

import com.example.boostmem.R
import com.example.boostmem.databinding.FragmentGameOverBinding

/**
 * A simple [Fragment] subclass.
 */
class GameOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameOverBinding>(inflater,R.layout.fragment_game_over,container,false)
        val args = GameOverFragmentArgs.fromBundle(arguments!!)
        binding.tryAgainButton.setOnClickListener {
            if(args.gameType == "Match"){
                findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToMatchingGameFragment(args.deck))
            }else if(args.gameType == "Quiz"){
                findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToQuizFragment(args.deck))
            }
        }
        binding.exitButton.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

}
