package com.mpierucci.android.redux.search.middlewares

import androidx.lifecycle.viewModelScope
import com.mpierucci.android.redux.drink.domain.GetDrinksByNameUseCase
import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import com.mpierucci.android.redux.redux.experimental.Middleware
import com.mpierucci.android.redux.redux.experimental.Store
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchAction.LoadSearchResults
import com.mpierucci.android.redux.search.SearchAction.Search
import com.mpierucci.android.redux.search.SearchState
import kotlinx.coroutines.launch
import javax.inject.Inject

class PerformSearchMiddleware @Inject constructor(
    private val getDrinksByNameUseCase: GetDrinksByNameUseCase,
    private val dispatcherProvider: DispatcherProvider
) : Middleware<SearchState, SearchAction> {
    override fun invoke(store: Store<SearchState, SearchAction>): (SearchAction) -> SearchAction {
        return { action ->
            when (action) {
                is Search -> {
                    store.viewModelScope.launch(dispatcherProvider.main()) {
                        val drinks = getDrinksByNameUseCase.execute(action.query)
                        store.dispatch(LoadSearchResults(drinks))
                    }
                    action //search will reduce de state to show loading..
                }
                else -> action
            }
        }
    }
}