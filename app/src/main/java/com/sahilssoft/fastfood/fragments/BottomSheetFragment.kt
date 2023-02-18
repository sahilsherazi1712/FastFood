package com.sahilssoft.fastfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sahilssoft.fastfood.R
import com.sahilssoft.fastfood.activities.MainActivity
import com.sahilssoft.fastfood.activities.MealActivity
import com.sahilssoft.fastfood.databinding.FragmentBottomSheetBinding
import com.sahilssoft.fastfood.viewModel.HomeViewModel

private const val MEAL_ID = "param1"
class BottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        homeViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            homeViewModel.getBottomMealById(mealId!!)
        }

        var mealName : String? = null
        var mealThumb : String? = null
        homeViewModel.observeBottomLiveData().observe(viewLifecycleOwner){
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgBs)

            binding.tvAreaBs.text = it.strArea
            binding.tvCategoryBs.text = it.strCategory
            binding.tvMealNameBs.text = it.strMeal

            mealName = it.strMeal
            mealThumb = it.strMealThumb
        }

        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null){
                val intent = Intent(requireActivity(),MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}