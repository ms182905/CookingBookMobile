package com.soltys.cookingbookmobile.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.soltys.cookingbookmobile.R
import com.soltys.cookingbookmobile.model.Ingredient
import com.soltys.cookingbookmobile.model.Recipe
import com.squareup.picasso.Picasso

class RecipeIngredientsAdapter(private val context : Activity, private val arrayList : ArrayList<Ingredient>) : ArrayAdapter<Ingredient>(
    context, R.layout.ingredient_item, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.ingredient_item, null)

        val ingredient : TextView = view.findViewById(R.id.ingredientItem)

        ingredient.text = formatText(arrayList[position].text)

        return view
    }

    private fun formatText(input: String) : String {
        val regex = "<a.*a>"
        return input.replace(regex.toRegex(), "")
    }
}