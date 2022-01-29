package com.mpierucci.android.redux.search

import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkError


data class SearchState(
    val query: String = "",
    val drinks: List<Drink>? = null,
    val loading: Boolean = false,
    val error: DrinkError? = null
)
