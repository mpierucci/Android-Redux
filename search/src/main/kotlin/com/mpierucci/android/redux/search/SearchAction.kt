package com.mpierucci.android.redux.search

import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkError

sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
    data class LoadSearchResults(val drinks: List<Drink>) : SearchAction()
    data class AppendSearchQuery(val querySlice: String) : SearchAction()
    data class DisplayError(val error:DrinkError): SearchAction()
}