package com.sahilssoft.fastfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahilssoft.fastfood.databinding.ItemPopularBinding
import com.sahilssoft.fastfood.pojo.MealCategoryMeal

class PopularAdapter(): RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    lateinit var onItemClick:((MealCategoryMeal)-> Unit)
    lateinit var onLongItemClick:((MealCategoryMeal)-> Unit)
    private var list = ArrayList<MealCategoryMeal>()


    fun setMeals(list: ArrayList<MealCategoryMeal>){
        this.list = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPopularBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(list[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(list[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick.invoke(list[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemPopularBinding): RecyclerView.ViewHolder(binding.root)
}
