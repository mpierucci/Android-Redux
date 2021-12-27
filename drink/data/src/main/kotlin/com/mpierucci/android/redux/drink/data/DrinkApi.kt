package com.mpierucci.android.redux.drink.data

import arrow.core.Either
import com.mpierucci.android.redux.core.data.HttpError
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface DrinkApi {
    @GET("api/json/v1/1/search.php")
    fun getDrinksByName(@Query("s") name: String): Call<Either<HttpError, DrinksByNameResponse>>
}