package com.sahilssoft.fastfood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.sahilssoft.fastfood.R
import com.sahilssoft.fastfood.adapters.CategoryMealsAdapter
import com.sahilssoft.fastfood.databinding.ActivityCategoryMealsBinding
import com.sahilssoft.fastfood.fragments.HomeFragment
import com.sahilssoft.fastfood.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMvvm: CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMvvm = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
        categoryMealsAdapter = CategoryMealsAdapter()

        categoryMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMvvm.observeMealsByCategory().observe(this){
            Log.e("categoryMealData",it.toString())

            binding.tvCategoryCount.text = "Count: ${it.size}"
            categoryMealsAdapter.setCategoryMeals(it)
            categoryMealsAdapter.onItemClick = {
                val intent = Intent(this,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,it.idMeal)
                    putExtra(HomeFragment.MEAL_NAME,it.strMeal)
                    putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)
                }
                startActivity(intent)
            }
            binding.rvCategoryMeals.adapter = categoryMealsAdapter
        }
    }
}