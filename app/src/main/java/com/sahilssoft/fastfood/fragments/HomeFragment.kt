package com.sahilssoft.fastfood.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.sahilssoft.fastfood.activities.CategoryMealsActivity
import com.sahilssoft.fastfood.activities.MainActivity
import com.sahilssoft.fastfood.activities.MealActivity
import com.sahilssoft.fastfood.adapters.CategoryAdapter
import com.sahilssoft.fastfood.adapters.PopularAdapter
import com.sahilssoft.fastfood.databinding.FragmentHomeBinding
import com.sahilssoft.fastfood.pojo.MealCategoryMeal
import com.sahilssoft.fastfood.pojo.Meal
import com.sahilssoft.fastfood.viewModel.HomeViewModel
import kotlin.math.log

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    companion object{
        const val MEAL_ID = "com.sahilssoft.fastfood.fragments.idMeal"
        const val MEAL_NAME = "com.sahilssoft.fastfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.sahilssoft.fastfood.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.sahilssoft.fastfood.fragments.categoryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        homeMvvm = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeMvvm = (activity as MainActivity).viewModel
        popularAdapter = PopularAdapter()
        categoryAdapter = CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //comment this line and initialize it in HomeViewModel
        homeMvvm.getRandomMeal()
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) {
            Glide.with(this@HomeFragment)
                .load(it.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = it
        }

        binding.cardRandomMeal.setOnClickListener {
            val intent = Intent(requireActivity(),MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }

        homeMvvm.getPopularItems()
        homeMvvm.observePopularLiveData().observe(viewLifecycleOwner){
            popularAdapter.setMeals(it!! as ArrayList<MealCategoryMeal>)
            popularAdapter.onItemClick = {meal ->
                val intent = Intent(requireActivity(),MealActivity::class.java)
                intent.putExtra(MEAL_ID,meal.idMeal)
                intent.putExtra(MEAL_NAME,meal.strMeal)
                intent.putExtra(MEAL_THUMB,meal.strMealThumb)
                startActivity(intent)
            }

            popularAdapter.onLongItemClick = {
                val bottomSheetFragment = BottomSheetFragment.newInstance(it.idMeal)
                bottomSheetFragment.show(childFragmentManager,"Meal Info")
            }
            binding.rvMealsPopular.adapter = popularAdapter
        }

        homeMvvm.getCategories()
        homeMvvm.observeCategoryLiveData().observe(viewLifecycleOwner){categories ->
            categoryAdapter.setCategory(categories)
            categoryAdapter.onItemClick = {
                val intent = Intent(requireActivity(),CategoryMealsActivity::class.java)
                intent.putExtra(CATEGORY_NAME,it.strCategory)
                startActivity(intent)
            }
            binding.rvCategory.adapter = categoryAdapter
        }




//          without view model, direct call api
//        RetrofitInstance.api.getRandomMeal().enqueue(object: Callback<MealList>{
//            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
//                if (response.body()!=null){
//                    val randomMeal: Meal = response.body()!!.meals.get(0)
//                    Glide.with(this@HomeFragment)
//                        .load(randomMeal.strMealThumb)
//                        .into(binding.imgRandomMeal)
//                }else{
//                    return
//                }
//            }
//
//            override fun onFailure(call: Call<MealList>, t: Throwable) {
//                Log.e("HomeFragment", t.message.toString())
//            }
//        })

    }
}