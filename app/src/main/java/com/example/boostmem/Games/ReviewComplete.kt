package com.example.boostmem.Games

import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.boostmem.R
import kotlinx.android.synthetic.main.activity_review_complete.*


class ReviewComplete : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_complete)

        check.check();


    }
}
