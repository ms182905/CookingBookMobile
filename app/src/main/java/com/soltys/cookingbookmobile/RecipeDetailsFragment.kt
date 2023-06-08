package com.soltys.cookingbookmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.soltys.cookingbookmobile.databinding.FragmentDetailsBinding
import com.soltys.cookingbookmobile.databinding.FragmentFirstBinding
import com.soltys.cookingbookmobile.view.RecipeAdapter
import com.soltys.cookingbookmobile.view.RecipeIngredientsAdapter
import com.soltys.cookingbookmobile.viewmodel.RecipeDetailsViewModel
import com.soltys.cookingbookmobile.viewmodel.RecipeListViewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private lateinit var recipeDetailsViewModel: RecipeDetailsViewModel

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
        arguments?.getString("apiId")?.let { binding.testingText.text = it }

        recipeDetailsViewModel = RecipeDetailsViewModel()

        arguments?.getString("phrase")?.let { recipeDetailsViewModel.getRecipeDetailsData(it) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}