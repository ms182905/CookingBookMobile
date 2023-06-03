package com.soltys.cookingbookmobile.model

import com.google.gson.annotations.SerializedName

data class RecipesResponse(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class RecipesItem(

	@field:SerializedName("instructions")
	val instructions: List<InstructionsItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("sections")
	val sections: List<SectionsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("thumbnail_url")
	val thumbnailUrl: String? = null,

)

data class SectionsItem( // lista skladnikow

	@field:SerializedName("components")
	val components: List<ComponentsItem?>? = null,
)

data class ComponentsItem( // skladnik

	@field:SerializedName("raw_text")
	val rawText: String? = null,
)

data class InstructionsItem( // krok instrukcji

	@field:SerializedName("display_text")
	val displayText: String? = null
)

data class ResultsItem(

	@field:SerializedName("instructions")
	val instructions: List<InstructionsItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("sections")
	val sections: List<SectionsItem?>? = null, //? - lista składnikow

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("thumbnail_url")
	val thumbnailUrl: String? = null,

	@field:SerializedName("recipes")
	val recipes: List<RecipesItem?>? = null
)
