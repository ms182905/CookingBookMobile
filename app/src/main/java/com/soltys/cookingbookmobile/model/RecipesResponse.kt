package com.soltys.cookingbookmobile.model

import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    @field:SerializedName("count") val count: Int? = null,
    @field:SerializedName("results") val results: List<ResultsItem?>? = null
)

data class RecipesItem(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("thumbnail_url") val thumbnailUrl: String? = null,
)

data class ResultsItem(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("thumbnail_url") val thumbnailUrl: String? = null,
    @field:SerializedName("recipes") val recipes: List<RecipesItem?>? = null
)
