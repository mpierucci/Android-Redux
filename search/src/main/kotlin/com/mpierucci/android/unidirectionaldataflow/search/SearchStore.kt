package com.mpierucci.android.unidirectionaldataflow.search

import arrow.core.Either
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.redux.Store
import com.mpierucci.android.unidirectionaldataflow.redux.state
import com.mpierucci.android.unidirectionaldataflow.search.middlewares.PerformSearchMiddleware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchStore @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    performSearchMiddleware: PerformSearchMiddleware
) : Store<SearchState, SearchAction, SearchViewEffect>(
    SearchState(),
    listOf(performSearchMiddleware),
    dispatcherProvider
) {
    override suspend fun reduce(
        previous: SearchState,
        action: SearchAction
    ): Either<SearchViewEffect, SearchState> {
        return when (action) {
            is SearchAction.AppendSearchQuery -> Either.state(previous.copy(query = action.querySlice))
            is SearchAction.LoadSearchResults -> Either.state(previous.copy(drinks = action.drinks))
            else -> Either.state(previous)
        }
    }
}