package com.mpierucci.android.redux.search.middlewares

import androidx.lifecycle.viewModelScope
import com.mpierucci.android.redux.navigation.NavigationDirections.DrinkDetails
import com.mpierucci.android.redux.navigation.NavigationManager
import com.mpierucci.android.redux.redux.Middleware
import com.mpierucci.android.redux.redux.Store
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchState
import kotlinx.coroutines.launch
import javax.inject.Inject

class NavigationMiddleware @Inject constructor(
    private val navigationManager: NavigationManager
) : Middleware<SearchState, SearchAction> {
    override fun invoke(store: Store<SearchState, SearchAction>): (SearchAction) -> SearchAction {
        return { action ->
            when (action) {
                is SearchAction.NavigateToDrinkDetail -> {
                    store.viewModelScope.launch {
                        navigationManager.navigate(DrinkDetails.drinkDetails(action.drinkName))
                    }
                    action
                }
                else -> action
            }
        }
    }
}