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
import com.example.boostmem.databinding.FragmentReviewCompleteBinding
import com.example.boostmem.databinding.FragmentReviewCompleteBindingImpl
import kotlinx.android.synthetic.main.fragment_review_complete.*

/**
 * A simple [Fragment] subclass.
 */
class ReviewCompleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_review_complete, container, false)
        val binding = DataBindingUtil.inflate<FragmentReviewCompleteBinding>(inflater,R.layout.fragment_review_complete,container,false)
        val args = ReviewCompleteFragmentArgs.fromBundle(arguments!!)
        val old = binding.correctAnswersTextview.text
        binding.correctAnswersTextview.setText("${old} ${args.numQuestion}")
        binding.check.check()
        binding.doneButton.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}
