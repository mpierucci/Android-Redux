package com.mpierucci.android.unidirectionaldataflow.search

import com.mpierucci.android.unidirectionaldataflow.drink.domain.Drink

sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
    data class LoadSearchResults(val drinks: List<Drink>) : SearchAction()
    data class AppendSearchQuery(val querySlice: String) : SearchAction()
}