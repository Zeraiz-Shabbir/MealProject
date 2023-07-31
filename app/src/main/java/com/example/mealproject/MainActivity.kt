package com.example.mealproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the EditText for search input and the Search button
        var searchBar = findViewById<EditText>(R.id.editTextText)
        var searchText = ""
        var searchButton = findViewById<Button>(R.id.button)

        // Set an onClickListener for the Search button
        searchButton.setOnClickListener {
            // Hide the soft keyboard when the Search button is clicked
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            // Get the text from the search input
            searchText = searchBar.text.toString()
            // Construct the URL for API call with the search text
            val ARTICLE_SEARCH_URL =
                "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + searchText
            // Create an instance of the AsyncHttpClient to make the API call
            val client = AsyncHttpClient()
            client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    // Handle API call failure
                    Log.e(ControlsProviderService.TAG, "Failed to fetch articles: $statusCode")
                }

                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    try {
                        // API call successful, parse the JSON response
                        Log.e(json.toString(), json.toString())
                        val mealArray = JSONArray(json.jsonObject.get("meals").toString())
                        val models = mutableListOf<Meal>()

                        // Loop through the JSON array and create Meal objects
                        for (i in 0 until mealArray.length()) {
                            val meal = mealArray.getJSONObject(i)
                            val mealName = meal.getString("strMeal")
                            val mealImage = meal.getString("strMealThumb")

                            // Create a Meal object
                            val mealObject = Meal(mealName, mealImage)
                            Log.e(mealObject.toString(), "HELLO")

                            // Add the Meal object to the models list
                            models.add(mealObject)
                        }

                        // Find the RecyclerView and set its layout manager
                        val recyclerView = findViewById<RecyclerView>(R.id.list)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                        // Create and set the adapter for the RecyclerView
                        val adapter = MealAdapter(models, this@MainActivity)
                        recyclerView.adapter = adapter
                    } catch (e: JSONException) {
                        // Handle JSON parsing error
                        Log.e(ControlsProviderService.TAG, "Failed to parse response: $e")
                        Toast.makeText(
                            this@MainActivity,
                            "No recipes exist for this ingredient!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }
}