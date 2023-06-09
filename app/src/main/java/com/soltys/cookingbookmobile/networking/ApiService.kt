package com.soltys.cookingbookmobile.networking

import com.soltys.cookingbookmobile.model.RecipeDetailsResponse
import com.soltys.cookingbookmobile.model.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/list")
    fun getRecipes(
        @Query("q") phrase: String,
        @Query("from") from: String = "0",
        @Query("size") size: String = "10"
    ): Call<RecipesResponse>

    @GET("recipes/get-more-info")
    fun getRecipeDetails(
        @Query("id") id: String
    ): Call<RecipeDetailsResponse>
}