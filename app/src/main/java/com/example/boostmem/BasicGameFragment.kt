package com.example.boostmem

import android.content.Context
import android.graphics.Color.red
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.anychart.core.ui.Tooltip
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.Statistic
import com.example.boostmem.databinding.FragmentBasicGameBinding
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon
import com.tomergoldst.tooltips.ToolTip
import com.tomergoldst.tooltips.ToolTipsManager
import kotlinx.android.synthetic.main.fragment_basic_game.view.*


class BasicGameFragment : Fragment(),View.OnClickListener {
    lateinit var binding: FragmentBasicGameBinding
    lateinit var navController: NavController
    lateinit var deckViewModel: DeckViewModel
    var questionIndex = 0
    lateinit var currentQuestion : Card
    var back = ""
    var cards = mutableListOf<Card>()
    var numQuestions = 0
    var numCount = 0
    var score = mutableListOf<Int>()
    lateinit var statistic: Statistic
     var questions = mutableListOf<Card>()
    lateinit var ballon: Balloon
    lateinit var deck:Deck
    var now = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate<FragmentBasicGameBinding>(inflater, R.layout.fragment_basic_game, container, false)

        val args = BasicGameFragmentArgs.fromBundle(arguments!!)
        deck = args.deck
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_score) +"${deck.deckName}"
        deckViewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)

        val x= deckViewModel.allCards.observe(viewLifecycleOwner, Observer<List<Card>>(){
            cards = it.filter { it.ownerID == args.deck.deckID }.toMutableList()
            numQuestions = cards.size
            randomizeQuestions()
            binding.game = this
        })
        val y = deckViewModel.allStatistic.observe(viewLifecycleOwner,Observer<List<Statistic>>(){
            val s = it.filter { it.deckownerID == args.deck.deckID }
             if(!s.isNullOrEmpty()) {
                 statistic = s.first()
             }else{
                 statistic = Statistic(0,deckownerID = args.deck.deckID,playCount = 0,average = 0f )
             }
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         navController = Navigation.findNavController(view)


        binding.easy.setOnClickListener(this)
        binding.good.setOnClickListener(this)
        binding.average.setOnClickListener(this)
        binding.bad.setOnClickListener(this)
        binding.fail.setOnClickListener(this)
        binding.flipButton.setOnClickListener{
            ballon!!.showAlignTop(binding.flipButton)
        }
        binding.skipButton.setOnClickListener(this)
    }
    private fun setQuestion() {
        currentQuestion = cards[questionIndex]
        // randomize the answers into a copy of the array
        back = currentQuestion.backDesp
         ballon = context?.let {
             createBalloon(it) {
                 setArrowSize(10)
                 setWidthRatio(1.0f)
                 setHeight(65)
                 setArrowPosition(0.7f)
                 setCornerRadius(4f)
                 setAlpha(0.9f)
                 setText(back)
                 setTextColorResource(R.color.white)
                 setBackgroundColorResource(R.color.redButton)
                 setBalloonAnimation(BalloonAnimation.FADE)
                 setLifecycleOwner(lifecycleOwner)
             }
         }!!
        // and shuffle them
        now = "(${questionIndex+1}/${numQuestions})"

    }
        private fun randomizeQuestions() {
            cards.shuffle()
            questionIndex = 0
            setQuestion()
        }

    override fun onClick(v: View?) {
        when(v){
            binding.easy -> score.add(5)
            binding.good -> score.add(4)
            binding.average-> score.add(3)
            binding.bad ->score.add(2)
            binding.fail -> score.add(1)
            binding.skipButton -> {
                score.add(0)
                numCount ++
            }
        }
        questionIndex++
        ballon.dismiss()
        if(questionIndex < numQuestions){
            setQuestion()
            binding.invalidateAll()
        }else{
            val total = 5*numQuestions
            val percent  = score.sum()*100/total.toFloat()
            statistic.average = percent
            statistic.playCount +=1
            deckViewModel.insert(statistic)
            navController.navigate(BasicGameFragmentDirections.actionBasicFragmentToReviewCompleteFragment(numQuestions-numCount))
        }

    }


}
