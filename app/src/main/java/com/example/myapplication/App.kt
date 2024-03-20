package com.example.myapplication

import android.app.Application
import androidx.room.Room
//this class initializes a singleton instance of AppDatabase at the start of the app's lifecycle, and makes it accessible throughout the app using the App.database property.
class App : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "MealDB").build()
    }
}