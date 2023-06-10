package com.soltys.cookingbookmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.soltys.cookingbookmobile.databinding.FragmentListBinding
import com.soltys.cookingbookmobile.model.Recipe
import com.soltys.cookingbookmobile.model.RecipesResponse
import com.soltys.cookingbookmobile.view.RecipeAdapter
import com.soltys.cookingbookmobile.viewmodel.RecipeListViewModel

class RecipeListFragment : Fragment() {

  private var _binding: FragmentListBinding? = null
  private lateinit var recipeListViewModel: RecipeListViewModel
  private val binding
    get() = _binding!!
  private lateinit var recipeArrayList: ArrayList<Recipe>

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    _binding = FragmentListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recipeArrayList = ArrayList()

    recipeListViewModel = RecipeListViewModel()

    arguments?.getString("phrase")?.let { recipeListViewModel.getRecipeData(it) }

    binding.recipeListView.adapter = activity?.let { RecipeAdapter(it, recipeArrayList) }

    recipeListViewModel.recipesData.observe(viewLifecycleOwner) { recipesData ->
      setResults(recipesData)
      (binding.recipeListView.adapter as RecipeAdapter?)?.notifyDataSetChanged()
      binding.progressBar.visibility = View.INVISIBLE
      binding.recipeListView.visibility = View.VISIBLE
    }

    binding.recipeListView.setOnItemClickListener { _, _view, _, _ ->
      val bundle = Bundle()
      bundle.putString("apiId", _view.id.toString())
      findNavController().navigate(R.id.action_SecondFragment_to_recipeDetailsFragment, bundle)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun setResults(recipesData: RecipesResponse) {
    recipesData.results?.forEach { it ->
      val recipe = Recipe(it?.name!!, it.thumbnailUrl!!, it.description!!, it.id!!)
      recipeArrayList.add(recipe)
    }
  }
}
