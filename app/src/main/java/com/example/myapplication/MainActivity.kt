
//https://drive.google.com/file/d/1M655_6h_iex6c1Awze6MqJmogeCQATzO/view?usp=share_link
package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the instance of the AppDatabase and store it in a static field of the App class.
        App.database = AppDatabase.getInstance(applicationContext)


        // Find the "addButton", "searchButton","webButton" and "searchDBButton" buttons by their IDs and store them in variables.
        val addButton: Button = findViewById(R.id.button3)
        val searchButton: Button = findViewById(R.id.searchig)
        val searchDBButton: Button = findViewById(R.id.searchdb)
        val searchWebButton: Button = findViewById(R.id.search_web)


        // Get the MealDao from the AppDatabase.
        val mealDao = App.database.mealDao()

        // Set a click listener for the "addButton".
        addButton.setOnClickListener {
            // Uses coroutines to launch a new coroutine on the current thread.
            runBlocking {
                launch {
                    // Create a new Meal object with some hardcoded meals to add to DB.
                    val meal1 = Meal(
                        12,
                        "Sweet and Sour Pork",
                        null,
                        "Chinese",
                        "Preparation\r\n1. Crack the egg into a bowl. Separate the egg white and yolk.\r\n\r\nSweet and Sour Pork\r\n2. Slice the pork tenderloin into ips.\r\n\r\n3. Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white.\r\n\r\n4. Marinade the pork ips for about 20 minutes.\r\n\r\n5. Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce.\r\n\r\nSweet and Sour Pork\r\nCooking Inuctions\r\n1. Pour the cooking oil into a wok and heat to 190\u00b0C (375\u00b0F). Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\r\n\r\n2. Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\r\n\r\n3. Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\r\n\r\n4. Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\r\n\r\n5. Serve on a plate and add some coriander for decoration.",
                        "https://www.themealdb.com/images/media/meals/1529442316.jpg",
                        "Sweet",
                        "https://www.youtube.com/watch?v=mdaBIhgEAMo",
                        arrayListOf(
                            Ingredient("Pork", "1 lb"),
                            Ingredient("Salt", "1 pinch"),
                            Ingredient("Starch", "3 tsp"),
                            Ingredient("Light soy sauce", "2 tsp"),
                            Ingredient("Egg white", "1"),
                            Ingredient("Cooking oil", "1/4 cup"),
                            Ingredient("Tomato sauce", "1/4 cup"),
                            Ingredient("White sugar", "2 tbsp"),
                            Ingredient("Water", "1/4 cup"),
                            Ingredient("Vinegar", "2 tbsp"),
                        ),
                        "Pork")


                    // second meal object is created
                    val meal2 = Meal(15,"Chicken Marengo",null,"French","Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. Add the chicken legs and cook briefly on each side to colour them a little.\r\nPour in the passata, crumble in the stock cube and stir in the olives. Season with black pepper \u2013 you shouldn\u2019t need salt. Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like.","https://www.themealdb.com/images/media/meals/qpxvuq1511798906.jpg",null,"https://www.youtube.com/watch?v=U33HYUr-0Fw",
                        arrayListOf(Ingredient("Olive Oil","1 tbs"),
                            Ingredient("Mushrooms","300g"),
                            Ingredient("Chicken Legs","4"),
                            Ingredient("Passata","500g"),
                            Ingredient("Chicken Stock Cube","1"),
                            Ingredient("Black Olives","100g "),
                            Ingredient("Parsley","Chopped"),
                        ),"Chicken")

                    val meal3 = Meal(10,"Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber",null,"Vietnamese","Add'l ingredients: mayonnaise, siracha\r\n\r\n1\r\n\r\nPlace rice in a fine-mesh sieve and rinse until water runs clear. Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or until ready to serve.\r\n\r\n2\r\n\r\nMeanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. Trim, peel, and grate carrot.\r\n\r\n3\r\n\r\nIn a medium bowl, combine cucumber, juice from half the lime, \u00bc tsp sugar (\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\u2019d like. Season with salt and pepper.\r\n\r\n4\r\n\r\nHeat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\r\n\r\n5\r\n\r\nFluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.","https://www.themealdb.com/images/media/meals/qpxvuq1511798906.jpg",null,"",
                        arrayListOf(Ingredient("Rice","White"),
                            Ingredient("Onion","1"),
                            Ingredient("Lime","1"),
                            Ingredient("Garlic Clove","3"),
                            Ingredient("Cucumber","1"),
                            Ingredient("Carrots", "3 oz"),
                            Ingredient("Ground Beef","1 lb"),
                            Ingredient("Soy Sauce","2 oz"),
                        ),"Beef")

                    val meal4 = Meal(21,"Leblebi Soup",null,"Tunisian","Heat the oil in a large pot. Add the onion and cook until translucent.\r\nDrain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, then reduce the heat and cover. Simmer for 30 minutes.\r\nIn the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. Add the garlic and salt and pound to a fine paste.\r\nAdd the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\r\nSeason to taste with salt, pepper and lemon juice and serve hot.","https://www.themealdb.com/images/media/meals/x2fw9e1560460636.jpg","Soup","https://www.youtube.com/watch?v=BgRifcCwinY",
                        arrayListOf(Ingredient("Olive Oil","2 tbs"),
                        Ingredient("Onion","1 medium finely diced"),
                            Ingredient("Chickpeas","250g"),
                            Ingredient("Vegetable Stock","1.5L"),
                            Ingredient("Cumin","1 tsp "),
                            Ingredient("Salt","1/2 tsp"),
                            Ingredient("Harissa Spice","1 tsp "),
                            Ingredient("Pepper","Pinch"),
                            Ingredient("Lime","1/2 "),
                        ),"Vegetarian")



                    mealDao.insertMeals(meal1,meal2,meal3,meal4)

                    // This line is commented out, but if uncommented, would delete all meals from the database using the MealDao object.
                    //mealDao.deleteAllMeals()

                        }
                    }
                }
        // The following lines set click listeners for three buttons, each of which starts a different activity when clicked.
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        searchDBButton.setOnClickListener {
            val intent = Intent(this, SearchDB::class.java)
            startActivity(intent)
        }

        searchWebButton.setOnClickListener {
            // This block of code creates and shows an AlertDialog prompting the user to enter a search term.
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Search for meals")
            val input = EditText(this)
            builder.setView(input)
            builder.setPositiveButton("Search") { dialog, _ ->
                // When the user clicks the positive button, this code retrieves the search term entered by the user, starts the SearchWeb activity, and passes the search term as an extra
                val searchTerm = input.text.toString()
                val intent = Intent(this@MainActivity, SearchWeb::class.java)
                intent.putExtra("query", searchTerm)
                startActivity(intent)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }





    }

}
