package com.bangkit.sehatin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var foodList: List<FoodItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.bind(foodItem)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(newFoodList: List<FoodItem>) {
        foodList = newFoodList
        notifyDataSetChanged()
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageFood: ImageView = itemView.findViewById(R.id.imageFood)
        private val textFoodName: TextView = itemView.findViewById(R.id.textFoodName)
        private val textCalories: TextView = itemView.findViewById(R.id.textCalories)

        fun bind(foodItem: FoodItem) {
            textFoodName.text = foodItem.food
            textCalories.text = "Calories: ${foodItem.calories}"

            // Load image using Glide
            Glide.with(itemView)
                .load(foodItem.image_Url)
                .into(imageFood)
        }
    }
}
