package com.sahilssoft.fastfood.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sahilssoft.fastfood.R
import com.sahilssoft.fastfood.activities.MainActivity
import com.sahilssoft.fastfood.activities.MealActivity
import com.sahilssoft.fastfood.adapters.FavouriteAdapter
import com.sahilssoft.fastfood.databinding.FragmentFavouritesBinding
import com.sahilssoft.fastfood.databinding.FragmentHomeBinding
import com.sahilssoft.fastfood.pojo.Meal
import com.sahilssoft.fastfood.pojo.MealCategoryMeal
import com.sahilssoft.fastfood.pojo.MealList
import com.sahilssoft.fastfood.viewModel.HomeViewModel

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var favouriteAdapter: FavouriteAdapter
    private lateinit var mealList: List<Meal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel
        favouriteAdapter = FavouriteAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.observerFavouritesMealsLiveData().observe(viewLifecycleOwner){
//            it.forEach {
//                Log.e("favouriteData", it.idMeal)
//            }
            mealList = it
            favouriteAdapter.setCategoryMeals(it)
//            favouriteAdapter.differ.submitList(it)
            favouriteAdapter.onItemClick = {
                val intent = Intent(requireActivity(), MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,it.idMeal)
                    putExtra(HomeFragment.MEAL_NAME,it.strMeal)
                    putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)
                }
                startActivity(intent)
            }
            binding.rvFavouriteMeals.itemAnimator = null
            binding.rvFavouriteMeals.adapter = favouriteAdapter
        }

        val itemTouchHelper =object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = mealList.get(viewHolder.adapterPosition)
//                val deletedMeal = favouriteAdapter.differ.currentList[position]
//                val deletedMeal = favouriteAdapter.getMelaByPosition(position)

                homeViewModel.deleteMeal(deletedMeal)
//                favouriteAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                Snackbar.make(requireView(),"Deleted: ${deletedMeal.strMeal!!}", Snackbar.LENGTH_LONG).setAction(
                    "UNDO",
                    View.OnClickListener {
                        homeViewModel.insertMeal(deletedMeal)
//                        favouriteAdapter.notifyItemChanged(position)
                    }
                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavouriteMeals)
    }
}