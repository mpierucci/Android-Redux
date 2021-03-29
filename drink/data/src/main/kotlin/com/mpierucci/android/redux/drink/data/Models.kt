package com.mpierucci.android.redux.drink.data

import com.mpierucci.android.redux.drink.domain.Ingredient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.mpierucci.android.redux.drink.domain.Drink as DomainDrink

private const val INGREDIENT_KEY = "ingredient"
private const val MEASURE_KEY = "measure"
private const val INGREDIENT_REGEX = "$INGREDIENT_KEY\\d+"

@Serializable
internal data class Drink(
    @SerialName("idDrink")
    val id: String,
    @SerialName("strDrink")
    val name: String,
    @SerialName("strTags")
    val tags: String?,
    @SerialName("strVideo")
    val videoUrl: String?,
    @SerialName("strInstructions")
    val instructions: String,
    @SerialName("strDrinkThumb")
    val thumbnail: String,
    @SerialName("strMeasure1")
    val measure1: String? = null,
    @SerialName("strMeasure2")
    val measure2: String? = null,
    @SerialName("strMeasure3")
    val measure3: String? = null,
    @SerialName("strMeasure4")
    val measure4: String? = null,
    @SerialName("strMeasure5")
    val measure5: String? = null,
    @SerialName("strMeasure6")
    val measure6: String? = null,
    @SerialName("strMeasure7")
    val measure7: String? = null,
    @SerialName("strMeasure8")
    val measure8: String? = null,
    @SerialName("strMeasure9")
    val measure9: String? = null,
    @SerialName("strMeasure10")
    val measure10: String? = null,
    @SerialName("strMeasure11")
    val measure11: String? = null,
    @SerialName("strMeasure12")
    val measure12: String? = null,
    @SerialName("strMeasure13")
    val measure13: String? = null,
    @SerialName("strMeasure14")
    val measure14: String? = null,
    @SerialName("strMeasure15")
    val measure15: String? = null,
    @SerialName("strIngredient1")
    val ingredient1: String? = null,
    @SerialName("strIngredient2")
    val ingredient2: String? = null,
    @SerialName("strIngredient3")
    val ingredient3: String? = null,
    @SerialName("strIngredient4")
    val ingredient4: String? = null,
    @SerialName("strIngredient5")
    val ingredient5: String? = null,
    @SerialName("strIngredient6")
    val ingredient6: String? = null,
    @SerialName("strIngredient7")
    val ingredient7: String? = null,
    @SerialName("strIngredient8")
    val ingredient8: String? = null,
    @SerialName("strIngredient9")
    val ingredient9: String? = null,
    @SerialName("strIngredient10")
    val ingredient10: String? = null,
    @SerialName("strIngredient11")
    val ingredient11: String? = null,
    @SerialName("strIngredient12")
    val ingredient12: String? = null,
    @SerialName("strIngredient13")
    val ingredient13: String? = null,
    @SerialName("strIngredient14")
    val ingredient14: String? = null,
    @SerialName("strIngredient15")
    val ingredient15: String? = null
)


internal fun Drink.toDomain(): DomainDrink {
    return DomainDrink(
        id,
        name,
        tags.orEmpty(),
        videoUrl,
        instructions,
        thumbnail,
        getIngredients()
    )
}

private fun Drink.getIngredients(): List<Ingredient> {
    return this::class.members.mapNotNull { ingredientCallable ->
        if (ingredientCallable.name.matches(Regex(INGREDIENT_REGEX))) {
            val measureProperty = this::class.members.find {
                it.name == ingredientCallable.name.replace(
                    INGREDIENT_KEY,
                    MEASURE_KEY
                )
            }
            val ingredient = ingredientCallable.call(this) as? String
            val measure = measureProperty?.call(this) as? String

            if (ingredient != null && measure != null) {
                Ingredient(ingredient, measure)
            } else {
                null
            }
        } else null
    }
}

