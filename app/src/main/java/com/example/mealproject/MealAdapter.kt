package com.example.mealproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealproject.R

class MealAdapter(private val meals: List<Meal>,
                    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<MealAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal, parent, false)
        return BookViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class BookViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        //variables for the views and images
        var mItem: Meal? = null
        val mName: TextView = mView.findViewById<View>(R.id.meal_title) as TextView
        val mImage: ImageView = mView.findViewById<View>(R.id.meal_image) as ImageView

        override fun toString(): String {
            return mName.toString() + " '"
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        //Binding the view and images
        val recipe = meals[position]
        holder.mItem = recipe
        holder.mName.text = recipe.MealName

        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }

        Glide.with(holder.mView)
            .load(recipe.MealImage)
            .centerInside()
            .into(holder.mImage)


    }
    override fun getItemCount(): Int {
        return meals.size
    }
}

class OnListFragmentInteractionListener {
    fun onItemClick(book: Meal) {

    }

}
