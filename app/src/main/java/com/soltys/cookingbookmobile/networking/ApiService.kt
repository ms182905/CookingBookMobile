package com.soltys.cookingbookmobile.networking

import com.soltys.cookingbookmobile.model.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/list")
    fun getRecipes(
        @Query("q") phrase: String,
        @Query("from") from: String = "0",
        @Query("size") size: String = "20",
    ): Call<RecipesResponse>
}