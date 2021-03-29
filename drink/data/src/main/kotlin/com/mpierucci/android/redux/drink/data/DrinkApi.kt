package com.mpierucci.android.redux.drink.data

import retrofit2.http.GET
import retrofit2.http.Query

internal interface DrinkApi {

    @GET("api/json/v1/1/search.php")
    suspend fun getDrinksByName(@Query("s") name: String): DrinksByNameResponse
}