package com.sahilssoft.fastfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahilssoft.fastfood.pojo.Meal
import com.sahilssoft.fastfood.pojo.MealCategoryList
import com.sahilssoft.fastfood.pojo.MealCategoryMeal
import com.sahilssoft.fastfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel(): ViewModel() {
    val categoryMealsLiveData = MutableLiveData<List<MealCategoryMeal>>()

    fun getMealsByCategory(categoryName: String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object: Callback<MealCategoryList>{
            override fun onResponse(
                call: Call<MealCategoryList>,
                response: Response<MealCategoryList>
            ) {
                response.body()?.let {
                    categoryMealsLiveData.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<MealCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModel",t.message.toString())
            }

        })
    }

    fun observeMealsByCategory(): LiveData<List<MealCategoryMeal>>{
        return categoryMealsLiveData
    }
}