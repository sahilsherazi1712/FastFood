package com.sahilssoft.fastfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahilssoft.fastfood.databinding.ItemMealBinding
import com.sahilssoft.fastfood.pojo.MealCategoryMeal

class CategoryMealsAdapter(): RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {
    private var list = ArrayList<MealCategoryMeal>()
    lateinit var onItemClick: ((MealCategoryMeal) -> Unit)

    fun setCategoryMeals(list: List<MealCategoryMeal>){
        this.list = list as ArrayList<MealCategoryMeal>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMealBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list.get(position)
        Glide.with(holder.itemView)
            .load(model.strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMeal.text = model.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(model)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemMealBinding): RecyclerView.ViewHolder(binding.root)
}