package com.example.myapplication.model

import android.graphics.RectF

class Result(val id: String?, val title: String?, val confidence: Float?, private var location: RectF?) {
    override fun toString(): String {
        var resultString = ""
        if (title != null) resultString += title
//        if (confidence != null) resultString += confidence.toString() // 몇 %의 확률인지 출력되는 부분
        return resultString
    }
}