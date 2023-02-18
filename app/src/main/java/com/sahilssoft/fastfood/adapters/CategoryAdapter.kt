package com.sahilssoft.fastfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahilssoft.fastfood.databinding.ItemCategoryBinding
import com.sahilssoft.fastfood.pojo.Category

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    lateinit var onItemClick: ((Category) -> Unit)
    private var list = ArrayList<Category>()

    fun setCategory(list: List<Category>){
        this.list = list as ArrayList<Category>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(list[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategory.text = list[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick.invoke(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)
}