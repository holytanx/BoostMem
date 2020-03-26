package com.example.boostmem

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boostmem.Adapter.MatchingGameRectyclerBackAdapter
import com.example.boostmem.Adapter.MatchingGameRecyclerAdapter
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.Statistic

import com.example.boostmem.databinding.FragmentMatchingGameBinding
import kotlinx.android.synthetic.main.fragment_matching_game.*
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class MatchingGameFragment : Fragment(),MatchingGameRectyclerBackAdapter.OnCardItemClickListener,MatchingGameRecyclerAdapter.OnCardItemClickListener {
    lateinit var matchingFrontAdapter: MatchingGameRecyclerAdapter
    lateinit var matchingBackAdapter: MatchingGameRectyclerBackAdapter
    lateinit var binding: FragmentMatchingGameBinding
    var list = arrayListOf<Card>()
    var random = arrayListOf<Card>()
    lateinit var deckViewModel: DeckViewModel
    var id: Long = 0
    var firstCard by Delegates.notNull<Int>()
    var secondCard by Delegates.notNull<Int>()
    var clickedFirst by Delegates.notNull<Int>()
    var clickedSecond by Delegates.notNull<Int>()
    var cardNumber = 1
    var chance = 3
    var numCorrects = 0
    var numIncorrects = 0
    lateinit var statistic: Statistic


    lateinit var deck: Deck
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val args = MatchingGameFragmentArgs.fromBundle(arguments!!)
        id = args.deck.deckID
        deck = args.deck
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.matchingGame)+"สำรับไพ่ ${deck.deckName}"

        deckViewModel = ViewModelProviders.of(this).get(DeckViewModel::class.java)

        binding = DataBindingUtil.inflate<FragmentMatchingGameBinding>(inflater,R.layout.fragment_matching_game,container,false)

        val x = deckViewModel.allCards.observe(viewLifecycleOwner, Observer {
            list = it.filter { it.ownerID == id } as ArrayList<Card>
            random = it.filter { it.ownerID == id }.shuffled() as ArrayList<Card>
            initFrontRecyclerView()
            initBackRecyclerView()
            Log.d("List",list.toString())
            matchingFrontAdapter.submitList(list)
            matchingBackAdapter.submitList(random)


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

    private fun initFrontRecyclerView(){
        binding.frontCard.layoutManager = LinearLayoutManager(context)
            matchingFrontAdapter = MatchingGameRecyclerAdapter(this)
            binding.frontCard.adapter = matchingFrontAdapter

    }
    private fun initBackRecyclerView(){
        binding.backCard.layoutManager = LinearLayoutManager(context)
        matchingBackAdapter = MatchingGameRectyclerBackAdapter(this)
            binding.backCard.adapter = matchingBackAdapter
        }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onItemClick(item: Card, position: Int) {
        checkBack(item,position)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkBack(item:Card, position: Int){
        if(cardNumber == 1){
            firstCard = item.cardID.toInt()
            clickedFirst = position
            cardNumber =2

        }
        else if(cardNumber == 2){
            secondCard = item.cardID.toInt()
            clickedSecond = position
             val handler = Handler()
             handler.postDelayed(Runnable {
                 run { calculate() }
             },1000)
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkFront(item:Card, position: Int){
        if(cardNumber == 1){
            firstCard = item.cardID.toInt()
            clickedFirst = position
            cardNumber = 2
        }
        else if(cardNumber == 2){
            secondCard = item.cardID.toInt()
            clickedSecond = position
            val handler = Handler()
            handler.postDelayed(Runnable {
                run { calculate() }
            },1000)
        }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculate() {
        if(firstCard == secondCard){
            if(list.isNotEmpty()){
                list.removeIf { it.cardID == firstCard.toLong() }
                random.removeIf { it.cardID == secondCard.toLong() }
                matchingFrontAdapter.notifyDataSetChanged()
                matchingBackAdapter.notifyDataSetChanged()
                numCorrects++
                if(list.isNullOrEmpty()){
                    val total = numCorrects+numIncorrects
                    val percent = (numCorrects*100f)/total
                    statistic.average = percent
                    statistic.playCount +=1
                    deckViewModel.insert(statistic)
                    findNavController().navigate(MatchingGameFragmentDirections.actionMatchingGameFragmentToPlayGameCompleteFragment(numIncorrects,numCorrects))

                }
            }

        }
        else if(firstCard != secondCard){
            chance--
            numIncorrects++
            when(chance){
                0 -> {
                    binding.heart1.isInvisible = true
                    binding.heart2.isInvisible = true
                    binding.heart3.isInvisible = true
                }
                1 -> {
                    binding.heart2.isInvisible = true
                    binding.heart3.isInvisible = true
                }
                2-> binding.heart3.isInvisible = true

                -1 -> {
                    statistic.playCount += 1
                    deckViewModel.insert(statistic)
                    findNavController().navigate(MatchingGameFragmentDirections.actionMatchingGameFragmentToGameOverFragment(deck,"Match"))
                }
            }
        }
        cardNumber = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onItemClickFront(item: Card, position: Int) {
        checkFront(item,position)
    }
}
