package com.mpierucci.android.redux.search.middlewares

import androidx.lifecycle.viewModelScope
import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import com.mpierucci.android.redux.drink.domain.GetDrinksByNameUseCase
import com.mpierucci.android.redux.redux.Middleware
import com.mpierucci.android.redux.redux.Store
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchAction.*
import com.mpierucci.android.redux.search.SearchState
import kotlinx.coroutines.launch
import javax.inject.Inject

class PerformSearchMiddleware @Inject constructor(
    private val getDrinksByNameUseCase: GetDrinksByNameUseCase
) : Middleware<SearchState, SearchAction> {
    override fun invoke(store: Store<SearchState, SearchAction>): (SearchAction) -> SearchAction {
        return { action ->
            when (action) {
                is Search -> {
                    store.viewModelScope.launch {
                        val newAction = getDrinksByNameUseCase(action.query).fold(
                            { error -> DisplayError(error) },
                            { drinks -> LoadSearchResults(drinks) }
                        )
                        store.dispatch(newAction)
                    }
                    action
                }
                else -> action
            }
        }
    }
}