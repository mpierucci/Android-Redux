package com.mpierucci.android.unidirectionaldataflow.drink.data

import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.drink.domain.Drink
import com.mpierucci.android.unidirectionaldataflow.drink.domain.DrinkRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DrinkRepository @Inject constructor(
    private val api: DrinkApi,
    private val dispatcherProvider: DispatcherProvider
) : DrinkRepository {
    override suspend fun getByName(drinkName: String): List<Drink> {
        val drinks = api.getDrinksByName(drinkName).drinks
        return withContext(dispatcherProvider.default()) {
            drinks?.map { it.toDomain() } ?: emptyList()
        }
    }
}