package com.example.mealproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ARTICLE_SEARCH_URL = "https://www.themealdb.com/api/json/v1/1/random.php"
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
                    val mealArray = JSONArray(json.jsonObject.get("meals").toString())
                    val meal = mealArray.getJSONObject(0)
                    val mealName = meal.getString("strMeal")
                    val mealCategory = meal.getString("strCategory")
                    val mealInstructions = meal.getString("strInstructions")
                    val mealImage = meal.getString("strMealThumb")
                    val mealIngredients = ArrayList<String>()
                } catch (e: JSONException) {
                    Log.e(ControlsProviderService.TAG, "Failed to parse response: $e")
                }
            }
        })
    }
}