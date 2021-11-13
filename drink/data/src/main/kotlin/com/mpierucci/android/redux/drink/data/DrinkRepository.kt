package com.mpierucci.android.redux.drink.data

import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DrinkRepository @Inject constructor(
    private val api: DrinkApi,
    private val dispatcherProvider: DispatcherProvider
) : DrinkRepository {
    override suspend fun getByName(drinkName: String): List<Drink> {
        return withContext(dispatcherProvider.io()) {
            val drinks = api.getDrinksByName(drinkName).execute().body()!!.drinks
            drinks?.map { it.toDomain() } ?: emptyList()
        }
    }
}
