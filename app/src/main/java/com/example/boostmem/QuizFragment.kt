package com.example.boostmem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.Statistic
import com.example.boostmem.databinding.FragmentQuizBinding
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.lang.String

/**
 * A simple [Fragment] subclass.
 */
class QuizFragment : Fragment(),View.OnClickListener {
    lateinit var deckViewModel: DeckViewModel
    lateinit var deck:Deck
    lateinit var navController: NavController
    lateinit var statistic: Statistic
     var buttonList = mutableListOf<Button>()
    var questionList = mutableListOf<Card>()
    var answerList = mutableListOf<Card>()
    var numQuestions = 0
    var numCorrects = 0
    var numIncorrects = 0
    var questionIndex = 0
    lateinit var currentQuestion : Card
    lateinit var binding: FragmentQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate<FragmentQuizBinding>(inflater,R.layout.fragment_quiz,container,false)
        val args = QuizFragmentArgs.fromBundle(arguments!!)
        deckViewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)

        deck = args.deck
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.quiz)+"สำรับไพ่ ${deck.deckName}"

        val x = deckViewModel.allCards.observe(viewLifecycleOwner, Observer {
            questionList =  it.filter { it.ownerID == deck.deckID }.toMutableList()
            numQuestions = questionList.size
            randomQuestions()
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
        navController = Navigation.findNavController(view)
        binding.firstAnswerButton.setOnClickListener(this)
        binding.secondAnswerButton.setOnClickListener(this)
        binding.thirdAnswerButton.setOnClickListener(this)
        binding.fourthAnswerButton.setOnClickListener(this)
        binding.skipButton.setOnClickListener(this)
        binding.flipButton.setOnClickListener(this)
    }

    private fun randomQuestions() {
        questionList.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        answerList.clear()
        currentQuestion = questionList[questionIndex]
        answerList.add(currentQuestion)
        val randomList = questionList.filter { it.cardID != currentQuestion.cardID }.take(3)
        for (i in randomList){
            answerList.add(i)
        }
        answerList.shuffle()
        answerCheck(answerList.size)
        binding.now.setText("${questionIndex+1}/${numQuestions}")


    }

    private fun answerCheck(size: Int) {
        if(size == 3){
            fourthAnswerButton.isInvisible = true
            binding.firstAnswerButton.setBackgroundColor(answerList.get(0).color)
            binding.secondAnswerButton.setBackgroundColor(answerList.get(1).color)
            binding.thirdAnswerButton.setBackgroundColor(answerList.get(2).color)
        }else if(size == 2){
            thirdAnswerButton.isInvisible =true
            fourthAnswerButton.isInvisible = true
            binding.firstAnswerButton.setBackgroundColor(answerList.get(0).color)
            binding.secondAnswerButton.setBackgroundColor(answerList.get(1).color)
        }else{
            binding.firstAnswerButton.setBackgroundColor(answerList.get(0).color)
            binding.secondAnswerButton.setBackgroundColor(answerList.get(1).color)
            binding.thirdAnswerButton.setBackgroundColor(answerList.get(2).color)
            binding.fourthAnswerButton.setBackgroundColor(answerList.get(3).color)
        }

    }

    override fun onClick(v: View?) {
        when(v!!){
            binding.firstAnswerButton -> {
                checkAnswer(0)
            }
            binding.secondAnswerButton -> {
                checkAnswer(1)
            }
            binding.thirdAnswerButton -> {
                checkAnswer(2)
            }
            binding.fourthAnswerButton -> {
                checkAnswer(3)
            }
        }
    }

    private fun checkAnswer(position:Int){
        if(answerList[position].cardID == currentQuestion.cardID ){
            numCorrects++
            questionIndex++
            if(questionIndex < numQuestions){
                setQuestion()
                binding.invalidateAll()
            }else{
                val total = numQuestions
                val percent = (numCorrects*100f)/total
                statistic.average = percent
                statistic.playCount++
                deckViewModel.insert(statistic)
                navController.navigate(QuizFragmentDirections.actionQuizFragmentToPlayGameCompleteFragment(numIncorrects,numCorrects))
            }
        }
        else{
            numIncorrects++
            if(numQuestions-numIncorrects < 0){
                navController.navigate(QuizFragmentDirections.actionQuizFragmentToGameOverFragment(deck,"Quiz"))
                statistic.playCount++
                deckViewModel.insert(statistic)
            }
        }
    }




}
