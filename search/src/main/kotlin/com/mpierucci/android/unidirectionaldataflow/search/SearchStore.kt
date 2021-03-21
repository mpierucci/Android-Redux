package com.mpierucci.android.unidirectionaldataflow.search

import arrow.core.Either
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.redux.Store

internal class SearchStore(
    dispatcherProvider: DispatcherProvider
) : Store<SearchState, SearchAction, SearchViewEffect>(
    SearchState(),
    emptyList(),
    dispatcherProvider
) {
    override suspend fun reduce(
        previous: SearchState,
        action: SearchAction
    ): Either<SearchViewEffect, SearchState> {
        TODO("Not yet implemented")
    }
}