package com.mpierucci.android.drinkdb.domain

class GetDrinksByNameUseCase(
    private val drinkRepository: DrinkRepository
) {

    suspend fun execute(drinkName: String): List<Drink> {
        return drinkRepository.getByName(drinkName)
    }
}