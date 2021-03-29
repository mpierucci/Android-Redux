package com.mpierucci.android.redux.search

import com.mpierucci.android.redux.drink.domain.Drink


data class SearchState(
    val query: String = "",
    val drinks: List<Drink> = emptyList()
)
