package com.example.myapplication.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.utils.Photo
import com.example.myapplication.view.RecommendFoodActivity


class ViewFoodAdapter(private val foodList: ArrayList<Photo>) :
    RecyclerView.Adapter<ViewFoodAdapter.MyPageViewHolder>() {

    class MyPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_food = itemView?.findViewById<ImageView>(R.id.iv_food_item)
        val tv_food = itemView?.findViewById<TextView>(R.id.tv_fname_item)


        fun bind(photo: Photo) {
            Log.d("리사이클러뷰 끼우기", photo.uri.toString() + photo.food_id)
            iv_food.setImageURI(photo.uri.toUri())
            tv_food.setText(photo.food_id)
        }
    }


    // 화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewFoodAdapter.MyPageViewHolder {
        // create a new view
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_food, parent, false)
        return MyPageViewHolder(itemView)
    }

    // 위의 onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결
    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        holder?.bind(foodList[position]!!)
        Log.d("리사이클러뷰 불러짐", "성공")

        holder?.itemView.setOnClickListener{
            val intent = Intent(holder.itemView?.context, RecommendFoodActivity::class.java)
            intent.putExtra("food_name",foodList[position].food_id)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

    }



    //  RecyclerView로 만들어지는 item의 총 개수를 반환
    override fun getItemCount() = foodList.size


}