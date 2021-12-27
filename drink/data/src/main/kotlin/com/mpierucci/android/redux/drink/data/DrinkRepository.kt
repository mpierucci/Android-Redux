package com.mpierucci.android.redux.drink.data

import arrow.core.Either
import com.mpierucci.android.redux.core.data.HttpError
import com.mpierucci.android.redux.core.data.NetworkError
import com.mpierucci.android.redux.core.data.retrofit.toEither
import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkError
import com.mpierucci.android.redux.drink.domain.DrinkRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DrinkRepository @Inject constructor(
    private val api: DrinkApi,
    private val dispatcherProvider: DispatcherProvider
) : DrinkRepository {
    override suspend fun getByName(drinkName: String): Either<DrinkError, List<Drink>> {
        return withContext(dispatcherProvider.io()) {
            val drinks = api.getDrinksByName(drinkName).toEither()
            drinks.fold(
                { error -> Either.left(error.toDrinkError()) },
                { response -> Either.right(response.drinks?.map { it.toDomain() } ?: emptyList()) }
            )
        }
    }

    private fun HttpError.toDrinkError(): DrinkError {
        return when (this) {
            is NetworkError -> DrinkError.NoConnection
            else -> DrinkError.Unknown
        }
    }
}
