package com.soltys.cookingbookmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.soltys.cookingbookmobile.databinding.FragmentSearchBinding


/**
 * A simple [Fragment] subclass as the default destination in the
 * navigation.
 */
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById(R.id.et_recipe_name) as EditText

        binding.buttonFirst.setOnClickListener {
            val editText: EditText = view.findViewById(R.id.et_recipe_name)
            val input = editText.text.toString()
            val bundle = Bundle()
            bundle.putString("phrase", input)

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }

        binding.goToFavouritesButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_favouriteRecipeListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}