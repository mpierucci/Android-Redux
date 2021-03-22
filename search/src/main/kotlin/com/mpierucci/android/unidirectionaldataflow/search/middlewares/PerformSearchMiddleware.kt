package com.mpierucci.android.unidirectionaldataflow.search.middlewares

import androidx.lifecycle.viewModelScope
import com.mpierucci.android.drinkdb.domain.GetDrinksByNameUseCase
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.redux.experimental.Middleware
import com.mpierucci.android.unidirectionaldataflow.redux.experimental.Store
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction.LoadSearchResults
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction.Search
import com.mpierucci.android.unidirectionaldataflow.search.SearchState
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Qualifier

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

interface Interface

class InterfaceImpl1:Interface
class InterfaceImpl2:Interface

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SearchStoreMiddlewares


@Module
object FakeModule {


    @Provides
    @SearchStoreMiddlewares
    fun provideSearchStoreMiddleWares(one:InterfaceImpl1,two:InterfaceImpl2): List<Interface> {
        return listOf(one,)
    }
}