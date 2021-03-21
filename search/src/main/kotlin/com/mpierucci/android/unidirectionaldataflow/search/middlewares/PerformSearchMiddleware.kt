package com.mpierucci.android.unidirectionaldataflow.search.middlewares

import com.mpierucci.android.drinkdb.domain.GetDrinksByNameUseCase
import com.mpierucci.android.unidirectionaldataflow.redux.DispatchChain
import com.mpierucci.android.unidirectionaldataflow.redux.Middleware
import com.mpierucci.android.unidirectionaldataflow.redux.Store
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction.LoadSearchResults
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction.Search
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal class PerformSearchMiddleware(
    private val getDrinksByNameUseCase: GetDrinksByNameUseCase
) : Middleware<SearchAction> {
    override fun apply(
        store: Store<*, SearchAction, *>,
        action: SearchAction,
        next: DispatchChain<SearchAction>
    ): SearchAction? {
        return when (action) {
            is Search -> {


                /*
                    TODO KEY question here:
                    should apply be suspend function or should the middleware require a coroutine cope

                    if it is suspend, will the suspend prevent dispathcign the original action unitl the fetch is done?
                    thus preventing other aciton dispatch?? TEST
                 */
                GlobalScope.launch {
                    val drinks = getDrinksByNameUseCase.execute(action.query)
                    store.dispatch(LoadSearchResults(drinks))
                }
                action //search will reduce de state to show loading..
            }
            else -> next.next(store, action)
        }
    }
}