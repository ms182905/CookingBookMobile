package com.soltys.cookingbookmobile

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.soltys.cookingbookmobile.databinding.FragmentFavouriteDetailsBinding
import com.soltys.cookingbookmobile.db.DBHelper
import com.soltys.cookingbookmobile.model.RecipeDetailsResponse
import com.soltys.cookingbookmobile.viewmodel.RecipeDetailsViewModel
import com.squareup.picasso.Picasso

class FavouriteRecipeDetailsFragment : Fragment() {

  private var _binding: FragmentFavouriteDetailsBinding? = null
  private lateinit var recipeDetailsViewModel: RecipeDetailsViewModel
  private lateinit var recipeDetailsData: RecipeDetailsResponse

  private val binding
    get() = _binding!!

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    _binding = FragmentFavouriteDetailsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recipeDetailsViewModel = RecipeDetailsViewModel()

    arguments?.getString("apiId")?.let { recipeDetailsViewModel.getRecipeDetailsData(it) }

    recipeDetailsViewModel.recipesData.observe(viewLifecycleOwner) { recipesData ->
      recipeDetailsData = recipesData
      setResults(recipesData)
    }

    binding.favouritesButton.setOnClickListener {
      val db = DBHelper(this.requireContext(), null)
      if (db.isInDatabase(recipeDetailsData.id.toString())) {
        binding.favouritesButton.drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
        db.removeRecipeFromDatabase(recipeDetailsData.id.toString())
        Toast.makeText(activity, "Removed from favourites", Toast.LENGTH_LONG).show()
      } else {
        binding.favouritesButton.drawable.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
        db.addRecipe(
            recipeDetailsData.id.toString(),
            recipeDetailsData.name!!,
            recipeDetailsData.description!!,
            recipeDetailsData.thumbnailUrl!!)

        Toast.makeText(activity, "Added to favourites", Toast.LENGTH_LONG).show()
      }

      println(recipeDetailsData.id)

      val recipeList = db.getRecipes()

      println(recipeList)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun setResults(recipeDetailsData: RecipeDetailsResponse) {
    var preparationSteps = ""
    recipeDetailsData.instructions?.forEach { it -> preparationSteps += it?.displayText!! + "\n\n" }

    var ingredients = ""
    recipeDetailsData.sections?.forEach { it ->
      it?.components?.forEach { component ->
        run {
          if (component?.rawText!! != "n/a") {
            ingredients += component?.rawText!! + "\n"
          }
        }
      }
    }

    ingredients.removeRange(ingredients.length - 2, ingredients.length - 1)

    val db = DBHelper(this.requireContext(), null)

    binding.recipeName.text = recipeDetailsData.name
    binding.recipeDescription.text = formatText(recipeDetailsData.description!!)
    binding.ingredientsView.text = ingredients
    binding.preparationStepsView.text = preparationSteps
    binding.editTextNotes.text =
        Editable.Factory.getInstance()
            .newEditable(db.getRecipeNote(recipeDetailsData.id.toString()))

    val imageView: ImageView = binding.recipePicture

    Picasso.with(context).load(recipeDetailsData.thumbnailUrl).into(imageView)

    binding.progressBar.visibility = View.INVISIBLE
    setVisible()

    binding.editTextNotes.addTextChangedListener(
        object : TextWatcher {
          override fun afterTextChanged(s: Editable) {
            db.updateRecipeNotes(recipeDetailsData.id.toString(), s.toString())
          }

          override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

          override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })


    if (db.isInDatabase(recipeDetailsData.id.toString())) {
      binding.favouritesButton.drawable.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
    } else {
      binding.favouritesButton.drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
    }
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
    binding.favouritesButton.visibility = View.VISIBLE
    binding.editTextNotes.visibility = View.VISIBLE
  }
}
