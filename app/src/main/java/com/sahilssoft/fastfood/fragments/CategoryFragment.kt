package com.sahilssoft.fastfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sahilssoft.fastfood.R
import com.sahilssoft.fastfood.activities.CategoryMealsActivity
import com.sahilssoft.fastfood.activities.MainActivity
import com.sahilssoft.fastfood.adapters.CategoryAdapter
import com.sahilssoft.fastfood.databinding.FragmentCategoryBinding
import com.sahilssoft.fastfood.viewModel.HomeViewModel

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryAdapter = CategoryAdapter()
        homeViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentCategoryBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getCategories()
        homeViewModel.observeCategoryLiveData().observe(viewLifecycleOwner){
            categoryAdapter.setCategory(it)
            categoryAdapter.onItemClick = {
                val intent = Intent(requireActivity(), CategoryMealsActivity::class.java)
                intent.putExtra(HomeFragment.CATEGORY_NAME,it.strCategory)
                startActivity(intent)
            }
            binding.rvCategory.adapter = categoryAdapter
        }
    }
}