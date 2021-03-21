package com.mpierucci.android.drinkdb.domain

interface DrinkRepository {

    suspend fun getByName(drinkName: String): List<Drink>
}