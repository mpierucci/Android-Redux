package com.mpierucci.android.redux.drink.domain

import arrow.core.Either

interface DrinkRepository {
    suspend fun getByName(drinkName: String): Either<DrinkError, List<Drink>>
}