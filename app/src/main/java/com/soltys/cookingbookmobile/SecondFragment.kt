package com.soltys.cookingbookmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.soltys.cookingbookmobile.databinding.FragmentSecondBinding
import com.soltys.cookingbookmobile.model.Recipe
import com.soltys.cookingbookmobile.model.RecipesResponse
import com.soltys.cookingbookmobile.networking.ApiService
import com.soltys.cookingbookmobile.view.RecipeAdapter
import com.soltys.cookingbookmobile.viewmodel.MainViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var mainViewModel: MainViewModel

     //This property is only valid between onCreateView and
     //onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recipeArrayList : ArrayList<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeArrayList = ArrayList()
        val recipe = arguments?.getString("phrase")?.let { Recipe(it, "asdasd", "sdfsdfsdfsdf") };

        if (recipe != null) {
            recipeArrayList.add(recipe)
        }

        mainViewModel = MainViewModel()

        arguments?.getString("phrase")?.let { mainViewModel.getRecipeData(it) }

        binding.listView.adapter = activity?.let { RecipeAdapter(it, recipeArrayList) }

        mainViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            setResults(weatherData)
            (binding.listView.adapter as RecipeAdapter?)?.notifyDataSetChanged()

        }



//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setResults(recipesData: RecipesResponse) {
        recipesData.results?.forEach {it ->
            val recipe = Recipe(it?.name!!, it?.thumbnailUrl!!, it?.description!!)
            recipeArrayList.add(recipe)
          }
        val recipe = Recipe(recipesData.results?.get(0)?.name!!, recipesData.results?.get(0)?.thumbnailUrl!!, recipesData.results?.get(0)?.description!!)
        recipeArrayList.add(recipe)
    }

}