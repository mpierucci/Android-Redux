package com.mpierucci.android.unidirectionaldataflow.search.middlewares

import androidx.lifecycle.viewModelScope
import com.mpierucci.android.drinkdb.domain.GetDrinksByNameUseCase
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.redux.DispatchChain
import com.mpierucci.android.unidirectionaldataflow.redux.Middleware
import com.mpierucci.android.unidirectionaldataflow.redux.Store
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction.LoadSearchResults
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction.Search
import kotlinx.coroutines.launch

class PerformSearchMiddleware(
    private val getDrinksByNameUseCase: GetDrinksByNameUseCase,
    private val dispatcherProvider: DispatcherProvider
) : Middleware<SearchAction> {
    override fun invoke(
        store: Store<*, SearchAction, *>,
        action: SearchAction,
        next: DispatchChain<SearchAction>
    ): SearchAction {
        return when (action) {
            is Search -> {
                store.viewModelScope.launch(dispatcherProvider.main()) {
                    val drinks = getDrinksByNameUseCase.execute(action.query)
                    store.dispatch(LoadSearchResults(drinks))
                }
                action //search will reduce de state to show loading..
            }
            else -> next(store, action)
        }
    }

}