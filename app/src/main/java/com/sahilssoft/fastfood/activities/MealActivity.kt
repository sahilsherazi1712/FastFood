package com.sahilssoft.fastfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.sahilssoft.fastfood.R
import com.sahilssoft.fastfood.databinding.ActivityMealBinding
import com.sahilssoft.fastfood.db.MealDatabase
import com.sahilssoft.fastfood.fragments.HomeFragment
import com.sahilssoft.fastfood.pojo.Meal
import com.sahilssoft.fastfood.viewModel.MealViewModel
import com.sahilssoft.fastfood.viewModel.MealViewModelFactory
import retrofit2.Callback

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]
        val mealDatabase = MealDatabase.getInstance(this)
        val mealViewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,mealViewModelFactory).get(MealViewModel::class.java)
        var mealToSave: Meal?= null

        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

        Glide.with(this)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        mealMvvm.observeMealDetailsLiveData().observe(this){
            onResponseCase()
            val meal = it
            mealToSave = meal

            binding.tvCategory.text = "Category: ${meal.strCategory}"
            binding.tvArea.text = "Area: ${meal.strArea}"
            binding.tvInstructionsTip.text = meal.strInstructions

            youtubeLink = meal.strYoutube!!
        }

        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal saved", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgYoutube.setOnClickListener {
            if(youtubeLink != null){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                startActivity(intent)
            }else{
                Toast.makeText(this,"No YouTube Video",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}