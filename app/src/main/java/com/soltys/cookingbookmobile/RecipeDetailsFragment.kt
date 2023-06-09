package com.soltys.cookingbookmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.soltys.cookingbookmobile.databinding.FragmentDetailsBinding
import com.soltys.cookingbookmobile.model.Ingredient
import com.soltys.cookingbookmobile.model.RecipeDetailsResponse
import com.soltys.cookingbookmobile.model.Step
import com.soltys.cookingbookmobile.viewmodel.RecipeDetailsViewModel
import com.squareup.picasso.Picasso

class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private lateinit var recipeDetailsViewModel: RecipeDetailsViewModel
    private lateinit var recipeIngredientsArrayList: ArrayList<Ingredient>
    private lateinit var recipePreparationStepsArrayList: ArrayList<Step>

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeIngredientsArrayList = ArrayList()
        recipePreparationStepsArrayList = ArrayList()

        recipeDetailsViewModel = RecipeDetailsViewModel()

        arguments?.getString("apiId")?.let { recipeDetailsViewModel.getRecipeDetailsData(it) }

        recipeDetailsViewModel.recipesData.observe(viewLifecycleOwner) { recipesData ->
            setResults(recipesData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setResults(recipeDetailsData: RecipeDetailsResponse) {
        var preparationSteps = ""
        recipeDetailsData.instructions?.forEach { it ->
            preparationSteps += it?.displayText!! + "\n\n"
        }

        var ingredients = ""
        recipeDetailsData.sections?.forEach { it ->
            it?.components?.forEach { component ->
                ingredients += component?.rawText!! + "\n"
            }
        }

        binding.recipeName.text = recipeDetailsData.name
        binding.recipeDescription.text = formatText(recipeDetailsData.description!!)
        binding.ingredientsView.text = ingredients
        binding.preparationStepsView.text = preparationSteps

        val imageView: ImageView = binding.recipePicture

        Picasso.with(context).load(recipeDetailsData.thumbnailUrl).into(imageView)

        binding.progressBar.visibility = View.INVISIBLE
        setVisible()
    }

    private fun formatText(input: String): String {
        val regex = "<a.*a>"
        return input.replace(regex.toRegex(), "")
    }

    private fun setVisible() {
        binding.ingredientsView.visibility = View.VISIBLE
        binding.preparationStepsView.visibility = View.VISIBLE
        binding.recipeName.visibility = View.VISIBLE
        binding.recipeDescription.visibility = View.VISIBLE
        binding.recipePicture.visibility = View.VISIBLE
        binding.ingredientsTitle.visibility = View.VISIBLE
        binding.preparationStepTitle.visibility = View.VISIBLE
    }
}