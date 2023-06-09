package com.soltys.cookingbookmobile.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.soltys.cookingbookmobile.R
import com.soltys.cookingbookmobile.model.Step

class RecipePreparationStepsAdapter(private val context : Activity, private val arrayList : ArrayList<Step>) : ArrayAdapter<Step>(
    context, R.layout.step_item, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.step_item, null)

        val step : TextView = view.findViewById(R.id.preparationStepItem)

        step.text = formatText(arrayList[position].text)

        return view
    }

    private fun formatText(input: String) : String {
        val regex = "<a.*a>"
        return input.replace(regex.toRegex(), "")
    }
}