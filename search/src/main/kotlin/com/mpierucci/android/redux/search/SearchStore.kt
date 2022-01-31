package com.mpierucci.android.redux.search

import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import com.mpierucci.android.redux.redux.Store
import com.mpierucci.android.redux.search.middlewares.NavigationMiddleware
import com.mpierucci.android.redux.search.middlewares.PerformSearchMiddleware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchStore @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    performSearchMiddleware: PerformSearchMiddleware,
    navigationMiddleware: NavigationMiddleware,
) : Store<SearchState, SearchAction>(
    SearchState(),
    listOf(performSearchMiddleware, navigationMiddleware),
    dispatcherProvider
) {
    override suspend fun reduce(
        previous: SearchState,
        action: SearchAction
    ): SearchState {
        return when (action) {
            is SearchAction.AppendSearchQuery -> previous.copy(query = action.querySlice)
            is SearchAction.LoadSearchResults -> previous.copy(
                drinks = action.drinks,
                loading = false,
                error = null
            )
            is SearchAction.Search -> previous.copy(loading = true)
            is SearchAction.DisplayError -> previous.copy(error = action.error, loading = false)
            is SearchAction.NavigateToDrinkDetail -> previous
        }
    }
}