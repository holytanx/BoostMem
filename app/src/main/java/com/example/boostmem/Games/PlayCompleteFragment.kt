package com.example.boostmem.Games

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.boostmem.MainActivity

import com.example.boostmem.R
import com.example.boostmem.databinding.FragmentPlayCompleteBinding

/**
 * A simple [Fragment] subclass.
 */
class PlayCompleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentPlayCompleteBinding>(inflater,R.layout.fragment_play_complete,container,false)
        binding.check.check()
        val args = PlayCompleteFragmentArgs.fromBundle(arguments!!)

        val correctTxt = binding.correctAnswersTextview.text
        val incorrectTxt = binding.incorrectAnswersTextview.text
        binding.correctAnswersTextview.setText("$correctTxt ${args.numCorrects}")
        binding.incorrectAnswersTextview.setText("$incorrectTxt ${args.numIncorrects}")

        binding.doneButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}
