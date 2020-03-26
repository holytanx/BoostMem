package com.example.boostmem

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.Statistic
import com.example.boostmem.Games.Gyroscope
import com.example.boostmem.databinding.FragmentYenchaGameBinding
import kotlinx.android.synthetic.main.fragment_yencha_game.*
import kotlin.math.round


class YenchaGameFragment : Fragment() , Gyroscope.Listener{

    lateinit var binding: FragmentYenchaGameBinding
    lateinit var countDownTimer: CountDownTimer
    private var timeLeft= 30000
    var numQuestions = 0
    var numCorrects = 0
    var numIncorrects = 0
    var questionIndex = 0
    var current : Float = 0f
    var delta : Float = 0f
    lateinit var currentQuestion : Card
    lateinit var deckViewModel: DeckViewModel
    var list = arrayListOf<Card>()
    lateinit var gyroscope : Gyroscope
    lateinit var deck:Deck
    lateinit var statistic: Statistic

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentYenchaGameBinding>(inflater,R.layout.fragment_yencha_game,container,false)
        val args = YenchaGameFragmentArgs.fromBundle(arguments!!)

        gyroscope = Gyroscope(activity!!)
        gyroscope.setListener(this)


        deckViewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)
        val x = deckViewModel.allCards.observe(viewLifecycleOwner, Observer {
            list = it.filter { it.ownerID == args.deck.deckID } as ArrayList<Card>
            numQuestions=list.size
            randomQuestion()
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

    private fun randomQuestion() {
        list.shuffle()
        questionIndex = 0
        currentQuestion = list[questionIndex]
        startTimer()
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = list[questionIndex]
        binding.now.setText("${questionIndex+1}/${numQuestions}")

    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeft.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdown_textview.setText((millisUntilFinished / 1000).toString())
            }
            override fun onFinish() {
                    countdown_textview.setText("Out of Time!")
            }
        }.start()
    }
    override fun onResume() {
        super.onResume()
        gyroscope.register()
    }

    override fun onPause() {
        super.onPause()
        gyroscope.unregister()
    }

    override fun onRotation(rotX: Float, rotY: Float, rotZ: Float) {
        val static = 0f
        when {
            // green
            round(rotX) < -3.5f -> {
                current = rotX
                delta = current - static
        }
            // red
            round(rotX) > 3.5f -> {
                current = rotX
                delta = current - static
            }
        }
        if(round(rotX) == static && delta != 0f){
            gyroscope.unregister()
            changeColor(delta)
        }
        Log.d("delta","${delta}")


    }

    private fun changeColor(delta: Float) {
          if (round(delta) > 0.0){
            binding.parentLayout.setBackgroundColor(Color.RED)
              changeQuestion(false)
          }
          else if(round(delta) < 0.0){
            binding.parentLayout.setBackgroundColor(Color.GREEN)
              changeQuestion(true)
          }
    }

    private fun changeQuestion(tick:Boolean) {
        if(tick){
            numCorrects++
        }else{
            numIncorrects++
        }
        questionIndex++
        if(questionIndex < numQuestions){
            setQuestion()
            binding.invalidateAll()

        }else if(questionIndex == numQuestions){
            gyroscope.unregister()
            countDownTimer.cancel()
            val total = numCorrects+numIncorrects
            val percent = (numCorrects*100f)/total
            statistic.average = percent
            statistic.playCount +=1
            deckViewModel.insert(statistic)
            findNavController().navigate(YenchaGameFragmentDirections.actionYenchaGameFragmentToPlayGameCompleteFragment(numIncorrects,numCorrects))

        }
        this.delta = 0f
        gyroscope.register()
    }


}
