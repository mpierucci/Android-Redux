package com.mpierucci.android.unidirectionaldataflow.search

import arrow.core.Either
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.redux.Middleware
import com.mpierucci.android.unidirectionaldataflow.redux.Store
import com.mpierucci.android.unidirectionaldataflow.redux.state

class SearchStore(
    dispatcherProvider: DispatcherProvider,
    middlewares: List<Middleware<SearchAction>>
) : Store<SearchState, SearchAction, SearchViewEffect>(
    SearchState(),
    middlewares,
    dispatcherProvider
) {
    override suspend fun reduce(
        previous: SearchState,
        action: SearchAction
    ): Either<SearchViewEffect, SearchState> {
        return when (action) {
            is SearchAction.AppendSearchQuery -> Either.state(previous.copy(query = action.querySlice))
            else -> Either.state(previous)
        }
    }
}