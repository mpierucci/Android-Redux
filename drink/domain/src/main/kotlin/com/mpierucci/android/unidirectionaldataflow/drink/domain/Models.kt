package com.mpierucci.android.unidirectionaldataflow.drink.domain

data class Drink(
    val id: String,
    val name: String,
    val tags: String,
    val videoUrl: String?,
    val instructions: String,
    val thumbnail: String,
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val measure: String
)