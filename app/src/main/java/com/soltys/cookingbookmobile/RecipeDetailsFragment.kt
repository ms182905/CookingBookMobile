package com.soltys.cookingbookmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.soltys.cookingbookmobile.databinding.FragmentDetailsBinding
import com.soltys.cookingbookmobile.model.*
import com.soltys.cookingbookmobile.view.RecipeIngredientsAdapter
import com.soltys.cookingbookmobile.view.RecipePreparationStepsAdapter
import com.soltys.cookingbookmobile.viewmodel.RecipeDetailsViewModel
import com.squareup.picasso.Picasso


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private lateinit var recipeDetailsViewModel: RecipeDetailsViewModel
    private lateinit var recipeIngredientsArrayList : ArrayList<Ingredient>
    private lateinit var recipePreparationStepsArrayList : ArrayList<Step>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeIngredientsArrayList = ArrayList()
        recipePreparationStepsArrayList = ArrayList()

        arguments?.getString("apiId")?.let { binding.testingText.text = it }

        recipeDetailsViewModel = RecipeDetailsViewModel()

        arguments?.getString("apiId")?.let { recipeDetailsViewModel.getRecipeDetailsData(it) }

        binding.ingredientsListView.adapter = activity?.let { RecipeIngredientsAdapter(it, recipeIngredientsArrayList) }
        binding.preparationStepsListView.adapter = activity?.let { RecipePreparationStepsAdapter(it, recipePreparationStepsArrayList) }

        recipeDetailsViewModel.recipesData.observe(viewLifecycleOwner) { recipesData ->
            setResults(recipesData)
            (binding.ingredientsListView.adapter as RecipeIngredientsAdapter?)?.notifyDataSetChanged()
            (binding.preparationStepsListView.adapter as RecipePreparationStepsAdapter?)?.notifyDataSetChanged()

            //setListViewHeightBasedOnChildren(binding.ingredientsListView)
            //setListViewHeightBasedOnChildren(binding.preparationStepsListView)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setResults(recipeDetailsData: RecipeDetailsResponse) {
        recipeDetailsData.instructions?.forEach {it ->
            recipePreparationStepsArrayList.add(Step(it?.displayText!!))
        }

        recipeDetailsData.sections?.forEach {it ->
            it?.components?.forEach{component ->
                recipeIngredientsArrayList.add(Ingredient(component?.rawText!!))
            }
        }

        binding.recipeName.text = recipeDetailsData.name
        binding.recipeDescription.text = formatText(recipeDetailsData.description!!)

        val imageView : ImageView = binding.recipePicture

        Picasso.with(context)
            .load(recipeDetailsData.thumbnailUrl)
            .into(imageView)


    }

    private fun formatText(input: String) : String {
        val regex = "<a.*a>"
        return input.replace(regex.toRegex(), "")
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter: ListAdapter = listView.adapter

        var totalHeight = 0
        for (i in 0 until listAdapter.getCount()) {
            val listItem: View = listAdapter.getView(i, null, listView)
            listItem.measure(0, 0)
            println("-----------------------------------")
            println(listItem.measuredHeight)

            totalHeight += listItem.measuredHeight
        }
        val params: ViewGroup.LayoutParams = listView.getLayoutParams()
        params.height =
            totalHeight + listView.getDividerHeight() * (listAdapter.getCount() - 1)
        listView.setLayoutParams(params)
    }
}