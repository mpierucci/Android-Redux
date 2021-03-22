package com.mpierucci.android.unidirectionaldataflow.search

import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.redux.experimental.Store
import com.mpierucci.android.unidirectionaldataflow.search.middlewares.PerformSearchMiddleware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchStore @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    performSearchMiddleware: PerformSearchMiddleware
) : Store<SearchState, SearchAction>(
    SearchState(),
    listOf(performSearchMiddleware),
    dispatcherProvider
) {
    override suspend fun reduce(
        previous: SearchState,
        action: SearchAction
    ): SearchState {
        return when (action) {
            is SearchAction.AppendSearchQuery -> previous.copy(query = action.querySlice)
            is SearchAction.LoadSearchResults -> previous.copy(drinks = action.drinks)
            else -> previous
        }
    }
}