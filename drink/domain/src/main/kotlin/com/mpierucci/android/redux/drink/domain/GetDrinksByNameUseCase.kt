package com.mpierucci.android.redux.drink.domain

import javax.inject.Inject

class GetDrinksByNameUseCase @Inject constructor(
    private val drinkRepository: DrinkRepository
) {
    suspend operator fun invoke(drinkName: String) : List<Drink>{
        return drinkRepository.getByName(drinkName)
    }
}