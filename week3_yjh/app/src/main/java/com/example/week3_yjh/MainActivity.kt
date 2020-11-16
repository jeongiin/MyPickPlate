package com.example.week3_yjh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonA.setOnClickListener{
            val intentA = Intent(this@MainActivity, AActivity::class.java)
            startActivity(intentA)
        }

        buttonB.setOnClickListener{
            val intentB=Intent(this@MainActivity, BActivity::class.java)
            startActivity(intentB)
        }

        buttonC.setOnClickListener{
            val intentC=Intent(this@MainActivity, CActivity::class.java)
            startActivity(intentC)
        }



    }
}

