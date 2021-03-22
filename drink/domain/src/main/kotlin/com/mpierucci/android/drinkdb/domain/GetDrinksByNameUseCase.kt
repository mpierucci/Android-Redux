package com.mpierucci.android.drinkdb.domain

import javax.inject.Inject

class GetDrinksByNameUseCase @Inject constructor(
    private val drinkRepository: DrinkRepository
) {
    suspend fun execute(drinkName: String): List<Drink> {
        return drinkRepository.getByName(drinkName)
    }
}