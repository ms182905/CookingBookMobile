package com.soltys.cookingbookmobile.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.soltys.cookingbookmobile.R
import com.soltys.cookingbookmobile.model.RecipesResponse
import com.soltys.cookingbookmobile.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var etRecipeName: EditText
    private lateinit var imgCondition: ImageView
    private lateinit var tvResult: TextView
    private lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = MainViewModel()
        subscribe()

        etRecipeName = findViewById(R.id.et_recipe_name)
        imgCondition = findViewById(R.id.img_condition)
        tvResult = findViewById(R.id.tv_result)
        btnSend = findViewById(R.id.btn_send_request)

        btnSend.setOnClickListener {
            if (etRecipeName.text.isNullOrEmpty() or etRecipeName.text.isNullOrBlank()) {
                etRecipeName.error = "Field can't be null"
            } else {
                mainViewModel.getRecipeData(etRecipeName.text.toString())
            }
        }
    }

    private fun subscribe() {
        mainViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) tvResult.text = resources.getString(R.string.loading)
        }

        mainViewModel.isError.observe(this) { isError ->
            if (isError) {
                imgCondition.visibility = View.GONE
                tvResult.text = mainViewModel.errorMessage
            }
        }

        mainViewModel.weatherData.observe(this) { weatherData ->
            setResultText(weatherData)
        }
    }

    private fun setResultText(recipesData: RecipesResponse) {
        val resultText = StringBuilder("Result:\n")

        recipesData.results?.get(0)?.name
        resultText.append("Name: ${recipesData.results?.get(0)?.name}\n")
        setResultImage(recipesData.results?.get(0)?.thumbnailUrl)

        tvResult.text = resultText
    }

    private fun setResultImage(imageUrl: String?) {
        imageUrl.let { url ->
            Glide.with(applicationContext)
                .load(url)
                .into(imgCondition)

            imgCondition.visibility = View.VISIBLE
            return
        }

        imgCondition.visibility = View.GONE
    }
}