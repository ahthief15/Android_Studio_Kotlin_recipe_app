package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SearchDB : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var mealDao: MealDao
    private lateinit var searchResultsLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchdb)

        // Initialize the views
        searchEditText = findViewById(R.id.search_edit_text)
        searchButton = findViewById(R.id.search_button)
        searchResultsLinearLayout = findViewById(R.id.search_results_linear_layout)

        // Initialize the mealDao
        mealDao = AppDatabase.getInstance(this).mealDao()

        // Set a click listener for the search button
        searchButton.setOnClickListener {
            // Get the search query from the EditText
            val searchQuery = searchEditText.text.toString().trim()

            // Query the database for meals with the search query in their name or ingredients
            SearchMealsTask(mealDao, searchQuery).execute()

        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class SearchMealsTask(private val mealDao: MealDao, private val searchQuery: String) :
        AsyncTask<Void, Void, List<Meal>>() {

        override fun doInBackground(vararg params: Void?): List<Meal> {
            Log.d("SearchMealsTask", "Starting search with query: $searchQuery")
            val results = mealDao.searchMeals("%$searchQuery%")
            Log.d("SearchMealsTask", "Found ${results.size} results")
            return results
        }

        override fun onPostExecute(result: List<Meal>) {
            // Clear any previous search results
            searchResultsLinearLayout.removeAllViews()

            if (result.isNotEmpty()) {
                for (meal in result) {
                    // Inflate the meal item layout
                    val mealItemLayout = layoutInflater.inflate(R.layout.meal_item, null)

                    // Get references to the views in the meal item layout
                    val nameTextView = mealItemLayout.findViewById<TextView>(R.id.meal_name_text_view)
                    val drinkTextView = mealItemLayout.findViewById<TextView>(R.id.meal_drink_text_view)
                    val AreaTextView = mealItemLayout.findViewById<TextView>(R.id.meal_area_text_view)
                    val instructionsTextView = mealItemLayout.findViewById<TextView>(R.id.meal_instructions_text_view)
                    val tagsTextView = mealItemLayout.findViewById<TextView>(R.id.meal_tags_text_view)
                    val youtubeTextView = mealItemLayout.findViewById<TextView>(R.id.meal_youtube_text_view)
                    val ingredientsTextView = mealItemLayout.findViewById<TextView>(R.id.meal_ingredients_text_view)
                    val measureTextView = mealItemLayout.findViewById<TextView>(R.id.meal_measure_text_view)
                    val imageView = mealItemLayout.findViewById<ImageView>(R.id.meal_image_view)

                    // Set the details of the meal to text view to display in UI
                    nameTextView.text = meal.name
                    drinkTextView.text = "Drink Alternative: ${meal.drinkAlternate}\n"
                    AreaTextView.text = "Area: ${meal.area}\n"
                    instructionsTextView.text = "Instructions: ${meal.instructions}\n"
                    tagsTextView.text = "Tags: ${meal.Tags}\n"
                    youtubeTextView.text = "Youtube: ${meal.Youtube}\n"
                    var ing = 1
                    var meas = 1
                    for (ingredient in meal.ingredients) {
                        ingredientsTextView.append("Ingredient${ing}: ${ingredient.name}\n\n")
                        ing++
                    }
                    for (ingredient in meal.ingredients) {
                        measureTextView.append("Measure${meas}: ${ingredient.measure}\n\n")
                        meas++
                    }

                    val mealim = meal.mealImageUrl

                    // Load the image from the URL using Glide
                    Glide.with(this@SearchDB)
                        .load(mealim)
                        .into(imageView)

                    // Add the meal item layout to the search results LinearLayout
                    searchResultsLinearLayout.addView(mealItemLayout)
                }
            } else {
                // If no meals were found, display a message
                val messageTextView = TextView(this@SearchDB)
                messageTextView.text = "No results found"
                messageTextView.setTextColor(Color.BLACK)
                searchResultsLinearLayout.addView(messageTextView)
            }
        }
    }

}

