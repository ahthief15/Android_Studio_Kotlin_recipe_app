package com.example.myapplication


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
//This is a data class representing a meal entity in the Room database. It has properties corresponding to columns in the database table.
@Entity
data class Meal(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "drinkAlternate") val drinkAlternate: String?,
    @ColumnInfo(name = "area") val area: String?,
    @ColumnInfo(name = "instructions") val instructions: String?,
    @ColumnInfo(name = "MealThumb") val MealThumb: String?,
    @ColumnInfo(name = "Tags") val Tags: String?,
    @ColumnInfo(name = "Youtube") val Youtube: String?,
    @TypeConverters(ListTypeConverter::class)
    @ColumnInfo(name = "Ingredients") val ingredients: ArrayList<Ingredient> = arrayListOf(),
    @ColumnInfo(name ="Category") val category:String?
) {
    val mealImageUrl: String
        get() = MealThumb ?: ""
}


