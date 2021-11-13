package com.mpierucci.android.redux.drink.domain

interface DrinkRepository {
    suspend fun getByName(drinkName: String): List<Drink>
}