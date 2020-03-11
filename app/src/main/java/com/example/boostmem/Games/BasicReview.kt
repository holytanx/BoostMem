package com.example.boostmem.Games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.boostmem.R
import kotlinx.android.synthetic.main.activity_basic_review.*

class BasicReview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_review)

        easy.setOnClickListener { view ->
            intent = Intent(this, ReviewComplete::class.java)
            startActivity(intent)
        }
    }
}
