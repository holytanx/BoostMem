package com.example.boostmem.Deck

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.boostmem.Database.Models.Category
import com.example.boostmem.Database.Models.Deck
import com.example.boostmem.DeckViewModel
import com.example.boostmem.MainActivity
import com.example.boostmem.R
import kotlinx.android.synthetic.main.activity_create_deck.*
import kotlinx.android.synthetic.main.add_cate_dialog.view.*
import org.jetbrains.anko.sdk25.coroutines.onItemSelectedListener
import java.lang.StringBuilder


class CreateDeck : AppCompatActivity() {
    lateinit var deckViewModel: DeckViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_deck)
        //declare variable
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        var selectedCate : Int = 0
        var items = mutableListOf<Category>()
        val spinner : Spinner = findViewById<Spinner>(R.id.category_Spinner)


        deckViewModel.allCates.observe(this, Observer {
            Log.i("categoryFromDB:","${it.last().categoryID} + ${it.last().categoryName} + size: ${it.size}")
            items = it.toMutableList()

            Log.i("categoryFromList:",items.size.toString())

            val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,items.map { it.categoryName })
            spinner.adapter = arrayAdapter
            arrayAdapter.notifyDataSetChanged()
        })
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                Toast.makeText(applicationContext,"Selected category: ${parent?.getItemAtPosition(position)} (ID : ${id})",Toast.LENGTH_LONG).show()
                Log.d("selectedCate",items.filter { it.categoryName.equals(parent?.getItemAtPosition(position).toString())}[0].categoryID.toString())
                var checkNull = items.filter { it.categoryName.equals(parent?.getItemAtPosition(position).toString()) }.firstOrNull()
                selectedCate = checkNull!!.categoryID
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }



        addDeck_button.setOnClickListener {
            var bundle = Bundle()
            val replyIntent = Intent()

            val deckName = deckName_EditText.text.toString().trim()
            val deckDescription = description_EditText.text.toString().trim()

            if (deckName.isEmpty()) {
                deckName_EditText.error = "required"
                deckName_EditText.requestFocus()
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val deck = Deck(0,deckName, selectedCate, deckDescription)
                replyIntent.putExtra(EXTRA_REPLY, deck )
                Log.d("added","ID: ${deck.deckID} Name: ${deck.deckName} CategoryID: ${deck.cateID}")
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        addCategory_button.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.add_cate_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Add category")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.addCate_dialog.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                //get text from EditTexts of custom layout
                val name = mDialogView.dialogNameEt.text.toString()
                if(name.isNullOrEmpty()){
                    Toast.makeText(applicationContext,"Please enter category name",Toast.LENGTH_LONG).show()
                }else{
                    deckViewModel.insert(Category(0,name))
                    Toast.makeText(applicationContext,"Successfully added : ${name}",Toast.LENGTH_LONG).show()

                }
                //set the input text in TextView
            }
            //cancel button click of custom layout
            mDialogView.cancel_dialog.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }

    }


    fun cancleAddDeck(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



    companion object {
        const val EXTRA_REPLY = "com.example.roombbdd.REPLY"
    }





}
