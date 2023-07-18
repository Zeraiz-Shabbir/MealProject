package com.example.mealproject

import android.widget.ImageView
import com.google.gson.annotations.SerializedName

class Meal (
    @SerializedName("strMeal")
    var MealName: String? = null,

    @SerializedName("strMealThumb")
    var MealImage: String? = null,

    @SerializedName("idMeal")
    var MealId: String? = null,
)
