package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
// This class is used to perform several function on meal entity
@Dao
interface MealDao {
    @Query("SELECT * FROM meal")
    suspend fun getAll(): List<Meal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(vararg meal: Meal)

    @Query("DELETE FROM meal")
    suspend fun deleteAllMeals()

    @Query("SELECT * FROM meal WHERE lower(name) LIKE lower(:searchQuery) OR lower(ingredients) LIKE lower(:searchQuery)")
    fun searchMeals(searchQuery: String): List<Meal>

}