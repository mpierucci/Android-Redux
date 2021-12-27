package com.mpierucci.android.redux.drink.domain

import arrow.core.Either
import javax.inject.Inject

class GetDrinksByNameUseCase @Inject constructor(
    private val drinkRepository: DrinkRepository
) {
    suspend operator fun invoke(drinkName: String) : Either<DrinkError, List<Drink>> {
        return drinkRepository.getByName(drinkName)
    }
}