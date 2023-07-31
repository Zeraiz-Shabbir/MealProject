package com.example.mealproject

import android.widget.ImageView
import com.google.gson.annotations.SerializedName

class Meal (
    //Meal class
    @SerializedName("strMeal")
    var MealName: String? = null,

    @SerializedName("strMealThumb")
    var MealImage: String? = null,
)
