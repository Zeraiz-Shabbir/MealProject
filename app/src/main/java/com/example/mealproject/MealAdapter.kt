package com.example.mealproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MealAdapter(private val meals: List<Meal>,
                  private val fragment: MainActivity
)
    : RecyclerView.Adapter<MealAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // Inflate the layout for a single item in the RecyclerView
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal, parent, false)
        return BookViewHolder(view)
    }

    inner class BookViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        // Declare variables for the views and images
        var mItem: Meal? = null
        val mName: TextView = mView.findViewById<View>(R.id.meal_title) as TextView
        val mImage: ImageView = mView.findViewById<View>(R.id.meal_image) as ImageView
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        // Bind the view and images with the data
        val recipe = meals[position]
        holder.mItem = recipe
        holder.mName.text = recipe.MealName

        // Load the image using Glide library
        Glide.with(holder.mView)
            .load(recipe.MealImage)
            .centerInside()
            .into(holder.mImage)
    }

    override fun getItemCount(): Int {
        // Return the number of items in the RecyclerView
        return meals.size
    }
}