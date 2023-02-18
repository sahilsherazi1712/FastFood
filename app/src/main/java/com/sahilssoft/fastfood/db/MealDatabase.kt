package com.sahilssoft.fastfood.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sahilssoft.fastfood.pojo.Meal

//abstract class is restricted class that cannot be used to create objects
// to access it. It must be inherit from another class. Abstract method can
//only be used in abstract class and does not have a body

@Database(entities = arrayOf(Meal::class), version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase: RoomDatabase() {

    abstract fun mealDao(): MealDao
    companion object{
        @Volatile
        var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }
}