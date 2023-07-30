package com.example.mealproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
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

        var searchBar = findViewById<EditText>(R.id.editTextText)
        var searchText = ""
        var searchButton = findViewById<Button>(R.id.button)
        searchButton.setOnClickListener {
            searchText = searchBar.text.toString()
            val ARTICLE_SEARCH_URL =
                "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + searchText
            val client = AsyncHttpClient()
            client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.e(ControlsProviderService.TAG, "Failed to fetch articles: $statusCode")
                }

                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    try {
                        Log.e(json.toString(), json.toString())
                        val mealArray = JSONArray(json.jsonObject.get("meals").toString())
                        val models = mutableListOf<Meal>()

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

                        val recyclerView = findViewById<RecyclerView>(R.id.list)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                        // Create and set the adapter for the RecyclerView
                        val adapter = MealAdapter(models, this@MainActivity)
                        recyclerView.adapter = adapter
                    } catch (e: JSONException) {
                        Log.e(ControlsProviderService.TAG, "Failed to parse response: $e")
                        Toast.makeText(this@MainActivity, "No recipes exist for this ingredient!", Toast.LENGTH_LONG).show()

                    }
                }
            })
        }
    }
}