package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recommed_food.*
import kotlinx.android.synthetic.main.activity_uploaded_food.*

class RecommendFoodActivity : AppCompatActivity() {

    private lateinit var food_name : String
    private var time : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommed_food)

        food_name = intent.getStringExtra("food_name")

        if (intent.hasExtra("food_name")) {
            tv_search_food.text = food_name
        } else {
            Toast.makeText(this, "Image Error!", Toast.LENGTH_SHORT).show()
        }


    }

//    public void onBackPressed(){
//        if(System.currentTimeMillis()-time>=2000){
//            time=System.currentTimeMillis();
//            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
//        }else if(System.currentTimeMillis()-time<2000){
//            finish();
//        }
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}