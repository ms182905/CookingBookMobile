package com.soltys.cookingbookmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soltys.cookingbookmobile.model.RecipeDetailsResponse
import com.soltys.cookingbookmobile.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetailsViewModel : ViewModel() {

  private val _recipeDetailsData = MutableLiveData<RecipeDetailsResponse>()
  val recipesData: LiveData<RecipeDetailsResponse>
    get() = _recipeDetailsData

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean>
    get() = _isLoading

  private val _isError = MutableLiveData<Boolean>()
  val isError: LiveData<Boolean>
    get() = _isError

  var errorMessage: String = ""
    private set

  fun getRecipeDetailsData(id: String) {
    _isLoading.value = true
    _isError.value = false

    val client = ApiConfig.getApiService().getRecipeDetails(id = id)

    client.enqueue(
        object : Callback<RecipeDetailsResponse> {

          override fun onResponse(
              call: Call<RecipeDetailsResponse>,
              response: Response<RecipeDetailsResponse>
          ) {
            val responseBody = response.body()
            if (!response.isSuccessful || responseBody == null) {
              onError("Data Processing Error")
              return
            }

            _isLoading.value = false
            _recipeDetailsData.postValue(responseBody!!)
          }

          override fun onFailure(call: Call<RecipeDetailsResponse>, t: Throwable) {
            onError(t.message)
            t.printStackTrace()
          }
        })
  }

  private fun onError(inputMessage: String?) {
    val message =
        if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error"
        else inputMessage

    errorMessage =
        StringBuilder("ERROR: ").append("$message some data may not displayed properly").toString()

    _isError.value = true
    _isLoading.value = false
  }
}
