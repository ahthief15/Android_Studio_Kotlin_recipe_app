package com.example.myapplication

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//This is a class that provides type conversion methods for Room to enable it to store and retrieve ArrayList<Ingredient> objects from the database.
class ListTypeConverter {


    @TypeConverter
    fun fromString(value: String): ArrayList<Ingredient> {
        val listType = object : TypeToken<ArrayList<Ingredient>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Ingredient>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}


