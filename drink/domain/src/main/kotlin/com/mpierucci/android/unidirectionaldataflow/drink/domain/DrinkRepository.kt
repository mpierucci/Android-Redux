package com.mpierucci.android.unidirectionaldataflow.drink.domain

interface DrinkRepository {

    suspend fun getByName(drinkName: String): List<Drink>
}