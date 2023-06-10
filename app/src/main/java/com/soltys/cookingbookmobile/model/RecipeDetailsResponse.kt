package com.soltys.cookingbookmobile.model

import com.google.gson.annotations.SerializedName

data class RecipeDetailsResponse(
    @field:SerializedName("instructions") val instructions: List<InstructionsItem?>? = null,
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("sections") val sections: List<SectionsItem?>? = null,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("thumbnail_url") val thumbnailUrl: String? = null
)

data class SectionsItem(
    @field:SerializedName("components") val components: List<ComponentsItem?>? = null
)

data class InstructionsItem(@field:SerializedName("display_text") val displayText: String? = null)

data class ComponentsItem(@field:SerializedName("raw_text") val rawText: String? = null)
