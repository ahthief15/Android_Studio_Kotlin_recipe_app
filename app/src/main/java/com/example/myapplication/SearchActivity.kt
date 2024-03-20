package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class SearchActivity : AppCompatActivity() {
    private lateinit var tv: TextView
    private lateinit var searchBox: EditText
    private lateinit var retrieveBtn: Button
    private lateinit var saveBtn: Button

    // Initialize a reference to the MealDao interface for interacting with the database
    private lateinit var mealDao: MealDao

    // Boolean flag to indicate whether the user wants to save the meal to the database or not
    var save = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        tv = findViewById(R.id.tv)
        searchBox = findViewById(R.id.search_box)
        retrieveBtn = findViewById(R.id.retrieve_btn)
        saveBtn = findViewById(R.id.save_btn)

        // Get an instance of the MealDao interface for interacting with the database
        mealDao = App.database.mealDao()


        // Sets an event listener on the retrieve button
        retrieveBtn.setOnClickListener {
            // Set the save flag to false, clear the text view, and retrieve the user's search query
            save = false
            tv.text = ""
            val ingredient = searchBox.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                // If the search query is not empty, call the function to search for meals by ingredient
                searchMealsByIngredient(ingredient)
            } else {
                // Otherwise, display a message asking the user to enter a search query
                Toast.makeText(this, "Please enter an ingredient", Toast.LENGTH_SHORT).show()
            }
        }

        // Set an event listener on the save button
        saveBtn.setOnClickListener {

            // Set the save flag to true, clear the text view, and retrieve the user's search query
            save = true
            tv.text = ""
            val ingredient = searchBox.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                // If the search query is not empty, call the function to search for meals by ingredient
                searchMealsByIngredient(ingredient)
                // If the save operation was successful, display a success message
                Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show()
            } else {
                // Otherwise, display a message indicating that the save operation was unsuccessful
                Toast.makeText(this, "Save Unsuccessful", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun searchMealsByIngredient(ingredient: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s=$ingredient"

        // launch a new coroutine to perform the network request and parse the response
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = URL(url).readText()
                parseJSON(result) // call the parseJSON() function to parse the JSON response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // a suspend function to parse the JSON response
    private suspend fun parseJSON(result: String) {
        val meals = mutableListOf<Meal>()

        // create a JSON object from the response string
        val json = JSONObject(result)

        // check if the "meals" array is null
        if (json.isNull("meals")) {
            // update the UI with a message indicating that no meals were found
            withContext(Dispatchers.Main) {
                tv.text = "No meals found for this ingredient"
            }
        } else {
            // retrieve the "meals" array from the JSON object
            val jsonArray = json.getJSONArray("meals")

            // loop through the array and extract the meal dat
            for (i in 0 until jsonArray.length()) {
                val mealJSON = jsonArray.getJSONObject(i)
                val id = mealJSON.getInt("idMeal")
                val name = mealJSON.getString("strMeal")
                val drinkAlternate = mealJSON.getString("strDrinkAlternate")
                val area = mealJSON.getString("strArea")
                val instructions = mealJSON.getString("strInstructions")
                val mealThumb = mealJSON.getString("strMealThumb")
                val tags = mealJSON.getString("strTags")
                val youtube = mealJSON.getString("strYoutube")
                val ingredients = mutableListOf<Ingredient>()

                // loop through the ingredient and measure strings and create Ingredient objects
                for (j in 1..20) {
                    val ingredientName = mealJSON.getString("strIngredient$j")
                    val ingredientMeasure = mealJSON.getString("strMeasure$j")
                    if (ingredientName.isNotEmpty() && ingredientMeasure.isNotEmpty()) {
                        ingredients.add(Ingredient(ingredientName, ingredientMeasure))
                    }
                }
                val category = mealJSON.getString("strCategory")

                // create a new Meal object with the extracted data and add it to the meals list
                val meal = Meal(
                    id,
                    name,
                    drinkAlternate,
                    area,
                    instructions,
                    mealThumb,
                    tags,
                    youtube,
                    ArrayList(ingredients),
                    category
                )
                meals.add(meal)
            }

            // update the UI with the meal data
            withContext(Dispatchers.Main) {
                tv.text = ""
                for (meal in meals) {
                    tv.append("Name: ${meal.name}\n\n")
                    tv.append("Area: ${meal.area}\n\n")
                    tv.append("Instructions: ${meal.instructions?.replace("\r\n", "")}\n\n")
                    tv.append("MealThumb: ${meal.MealThumb}\n\n")
                    tv.append("Tags: ${meal.Tags}\n\n")
                    tv.append("Youtube: ${meal.Youtube}\n\n")
                    tv.append("Category: ${meal.category}\n\n")
                    var ing = 1
                    var meas = 1
                    for (ingredient in meal.ingredients) {
                        tv.append("Ingredient${ing}: ${ingredient.name}\n\n")
                        ing++
                    }
                    for (ingredient in meal.ingredients) {
                        tv.append("Measure${meas}: ${ingredient.measure}\n\n")
                        meas++
                    }

                    tv.append("\n\n")
                    if (save == true) {
                        mealDao.insertMeals(meal)//if save button is clicked saves meal to local database
                    }

                }
            }
            }
        }
    }



