package com.example.my_firstpractice

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_intent1.*

class Intent1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent1)

        change_activity.setOnClickListener {
//            val intent = Intent(this@Intent1, Intent2::class.java)
//
//            // key, value 방식 -> key와 value를 쌍으로 만들어 저장한다 -> dictionary
//            intent.putExtra("number1", 1)
//            intent.putExtra("number2", 2)
//            startActivity(intent)

//            val intent2 = Intent(this@Intent1, Intent2::class.java)
//            // apply -> this라는 키워드를 사용하여 해당 인텐트에 적용
//            // 해당 인텐트에 하는 작업을 모아놓아 한 번에 볼 수 있음
//            intent2.apply {
//                intent.putExtra("number1", 1)
//                intent.putExtra("number2", 2)
//            }
//            startActivityForResult(intent2, 200)

            // 암시적 인텐트
            val intent=Intent(Intent.ACTION_VIEW,Uri.parse("http://m.naver.com"))
            startActivity(intent)


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 200) {
            Log.d("number", "" + requestCode)
            Log.d("number", "" + resultCode)
            val result = data?.getIntExtra("result", 0)
            Log.d("number","" + result)

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
