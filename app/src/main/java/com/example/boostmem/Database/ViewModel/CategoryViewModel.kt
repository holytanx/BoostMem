package com.example.boostmem.Database.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.boostmem.Database.AppDatabase
import com.example.boostmem.Database.Models.Category
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.Database.Repository.CategoryRepository
import com.example.boostmem.DeckRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class CategoryViewModel(application: Application) : AndroidViewModel(application){
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: CategoryRepository
    val allCates : LiveData<List<Category>>

    init {
        val cateDao = AppDatabase.getDatabase(application, scope).cateDao()
        repository = CategoryRepository(cateDao)
        allCates = repository.allCates
        if(allCates.value.isNullOrEmpty()){
            Toast.makeText(getApplication(),"Empty Category",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(getApplication(),"already have category",Toast.LENGTH_LONG).show()
        }
    }

    fun insert(cate: Category) = scope.launch(Dispatchers.IO) {
        repository.insert(cate)

    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    // BORRAR TODOS LOS DATOS
    fun deleteAll() {
        repository.deleteAll()
    }

    // BORRAR UN SOLO DATO
    fun deleteDeck(cate: Category) {
        repository.deleteCate(cate)
    }
}