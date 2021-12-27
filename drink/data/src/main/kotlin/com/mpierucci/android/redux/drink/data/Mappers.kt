package com.mpierucci.android.redux.drink.data

import com.mpierucci.android.redux.drink.domain.Ingredient

private const val INGREDIENT_KEY = "ingredient"
private const val MEASURE_KEY = "measure"
private const val INGREDIENT_REGEX = "$INGREDIENT_KEY\\d+"

internal fun Drink.toDomain(): com.mpierucci.android.redux.drink.domain.Drink {
    return com.mpierucci.android.redux.drink.domain.Drink(
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


