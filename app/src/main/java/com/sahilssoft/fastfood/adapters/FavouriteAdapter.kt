package com.sahilssoft.fastfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahilssoft.fastfood.databinding.ItemMealBinding
import com.sahilssoft.fastfood.pojo.Meal
import com.sahilssoft.fastfood.pojo.MealCategoryMeal

class FavouriteAdapter(): RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    private var list = ArrayList<Meal>()
    lateinit var onItemClick: ((Meal) -> Unit)

    fun setCategoryMeals(list: List<Meal>){
        this.list = list as ArrayList<Meal>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMealBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val model = differ.currentList.get(position)

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
//        return differ.currentList.size
        return list.size
    }

    inner class ViewHolder(val binding: ItemMealBinding): RecyclerView.ViewHolder(binding.root)

//    private val diffUtil = object : ItemCallback<Meal>(){
//        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
//            return oldItem.idMeal == newItem.idMeal
//        }
//
//        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
//            return oldItem == newItem
//        }
//    }
//    val differ = AsyncListDiffer(this,diffUtil)
}