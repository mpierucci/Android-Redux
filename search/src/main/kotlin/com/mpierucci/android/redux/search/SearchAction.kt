package com.mpierucci.android.redux.search

import com.mpierucci.android.redux.drink.domain.Drink

sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
    data class LoadSearchResults(val drinks: List<Drink>) : SearchAction()
    data class AppendSearchQuery(val querySlice: String) : SearchAction()
    object DisplayError: SearchAction()
}