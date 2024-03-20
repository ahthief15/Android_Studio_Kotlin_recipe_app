package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchWeb : AppCompatActivity() {
    lateinit var tv: TextView
    lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        tv = findViewById(R.id.tv)
        query = intent.getStringExtra("query") ?: ""

        if (query.isNotEmpty()) {
            // build the url based on the user entered meal name
            val url_string = "https://www.themealdb.com/api/json/v1/1/search.php?s=$query"

            // create url and connection objects
            val url = URL(url_string)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection

            runBlocking {
                launch {
                    withContext(Dispatchers.IO) {
                        val stb = StringBuilder()

                        // read data from the input stream
                        val bf = BufferedReader(InputStreamReader(con.inputStream))
                        var line: String? = bf.readLine()
                        while (line != null) {
                            stb.append(line + "\n")
                            line = bf.readLine()
                        }

                        // parse the JSON response
                        parseJSON(stb)
                    }
                }
            }
        } else {
            tv.text = "Please enter a meal name."
        }

    }

    suspend fun parseJSON(stb: StringBuilder) {
        // this contains the full JSON returned by the Web Service
        val json = JSONObject(stb.toString())

        // Information about all the meals extracted by this function
        var allMeals = StringBuilder()

        val jsonArray: JSONArray? = json.optJSONArray("meals")

        if (jsonArray != null) {
            // extract all the meals from the JSON array
            for (i in 0 until jsonArray.length()) {
                val meal: JSONObject = jsonArray[i] as JSONObject // this is a json object

                // extract the name
                val mealName = meal["strMeal"] as String

                // check if the meal name contains the user input substring
                if (mealName.toLowerCase().contains(query.toLowerCase())) {

                    allMeals.append("${i + 1}) \"$mealName\" ")

                    // extract the category
                    val category = meal["strCategory"] as String
                    allMeals.append("Category: $category\n")

                    allMeals.append("\n")
                }
            }

            // check if any meals were found
            if (allMeals.isEmpty()) {
                // Update the UI on the main thread
                runOnUiThread {
                    tv.text = "No meals found."
                }
            } else {
                // Update the UI on the main thread
                runOnUiThread {
                    tv.text = allMeals.toString()
                }
            }

        } else {
            // Update the UI on the main thread
            runOnUiThread {
                tv.text = "No meals found."
            }
        }
    }
}