package com.example.boostmem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.boostmem.Database.AppDatabase
import com.example.boostmem.Database.Models.Card
import com.example.boostmem.Database.Models.Category
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Models.NotificationModel
import com.example.boostmem.Database.Repository.CardRepository
import com.example.boostmem.Database.Repository.CategoryRepository
import com.example.boostmem.Database.Repository.NotiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DeckViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private val cateRepository: CategoryRepository
    private val deckRepository: DeckRepository
    private val cardRepository:CardRepository
    private val notiRepository:NotiRepository
    val allDecks : LiveData<List<Deck>>
    val allCates : LiveData<List<Category>>
    val allCards : LiveData<List<Card>>
    val allNoti : LiveData<List<NotificationModel>>
    init {
        val decksDao = AppDatabase.getDatabase(application, scope).deckDao()
        val catesDao = AppDatabase.getDatabase(application,scope).cateDao()
        val cardDao = AppDatabase.getDatabase(application,scope).cardDao()
        val notiDao = AppDatabase.getDatabase(application,scope).notiDao()

        deckRepository = DeckRepository(decksDao)
        allDecks = deckRepository.allDecks
        cateRepository = CategoryRepository(catesDao)
        allCates = cateRepository.allCates
        cardRepository = CardRepository(cardDao)
        allCards = cardRepository.allCards
        notiRepository = NotiRepository(notiDao)
        allNoti = notiRepository.allNoti
    }

    fun insert(deck: Deck) = scope.launch(Dispatchers.IO) {
        deckRepository.insert(deck)
    }
    fun insert(cate:Category) = scope.launch(Dispatchers.IO) {
        cateRepository.insert(cate)
    }
    fun insert(card:Card) = scope.launch(Dispatchers.IO){
        cardRepository.insert(card)
    }
    fun insert(notificationModel: NotificationModel) = scope.launch ( Dispatchers.IO ){
        notiRepository.insert(notificationModel)
    }
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    // BORRAR TODOS LOS DATOS
    fun deleteAll() {
        deckRepository.deleteAll()
    }

    // BORRAR UN SOLO DATO
    fun deleteDeck(deck:Deck) {
        deckRepository.deleteDeck(deck)
    }

    fun deleteNoti(notificationModel: NotificationModel){
        notiRepository.deleteNoti(notificationModel)
    }

    fun updateDeck(deck: Deck) {
        deckRepository.update(deck)
    }
    fun updateCard(card: Card) {
        cardRepository.update(card)
    }
    fun updateNoti(notificationModel: NotificationModel){
        notiRepository.update(notificationModel)
    }

    fun updateIsActiveNoti(myTaskParams: NotiRepository.MyTaskParams){
        notiRepository.updateIsActive(myTaskParams)
    }
}