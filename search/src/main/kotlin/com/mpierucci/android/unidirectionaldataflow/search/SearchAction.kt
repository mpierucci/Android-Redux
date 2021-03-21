package com.mpierucci.android.unidirectionaldataflow.search

import com.mpierucci.android.drinkdb.domain.Drink

internal sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
    data class LoadSearchResults(val drinks: List<Drink>) : SearchAction()
}