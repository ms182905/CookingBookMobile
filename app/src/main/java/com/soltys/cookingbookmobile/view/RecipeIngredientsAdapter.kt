package com.soltys.cookingbookmobile.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.soltys.cookingbookmobile.R
import com.soltys.cookingbookmobile.model.Recipe
import com.squareup.picasso.Picasso

class RecipeIngredientsAdapter(private val context : Activity, private val arrayList : ArrayList<Recipe>) : ArrayAdapter<Recipe>(
    context, R.layout.list_item, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item, null)
        view.id = arrayList[position].apiId

        val imageView : ImageView = view.findViewById(R.id.recipe_picture)
        val recipeName : TextView = view.findViewById(R.id.recipeName)
        val description : TextView = view.findViewById(R.id.recipeDescription)

        Picasso.with(context)
            .load(arrayList[position].picture_url)
            .into(imageView)

        recipeName.text = arrayList[position].name
        description.text = formatText(arrayList[position].description)

        return view
    }

    private fun formatText(input: String) : String {
        val regex = "<a.*a>"
        return input.replace(regex.toRegex(), "")
    }
}