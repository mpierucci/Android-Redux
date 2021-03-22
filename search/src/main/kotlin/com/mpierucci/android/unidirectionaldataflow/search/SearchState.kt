package com.mpierucci.android.unidirectionaldataflow.search

import com.mpierucci.android.drinkdb.domain.Drink


data class SearchState(
    val query: String = "",
    val drinks: List<Drink> = emptyList()
)
