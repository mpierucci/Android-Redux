package com.mpierucci.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.mpierucci.android.drinkdb.domain.Drink
import com.mpierucci.android.drinkdb.domain.DrinkRepository
import com.mpierucci.android.drinkdb.domain.GetDrinksByNameUseCase
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction
import com.mpierucci.android.unidirectionaldataflow.search.SearchStore
import com.mpierucci.android.unidirectionaldataflow.search.composables.SearchToolbar
import com.mpierucci.android.unidirectionaldataflow.search.middlewares.PerformSearchMiddleware

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fakeRepo = object : DrinkRepository {
            override suspend fun getByName(drinkName: String): List<Drink> {
                TODO("Not yet implemented")
            }
        }

        /*
        For the prupos of quick testing
         */
        val provider = object : DispatcherProvider {}
        val viewModel = SearchStore(
            provider,
            listOf(PerformSearchMiddleware(GetDrinksByNameUseCase(fakeRepo), provider))
        )
        setContent {
            searchScreenComposable(store = viewModel)
        }
    }
}


@Composable
fun searchScreenComposable(store: SearchStore) {


    /**
     * TODO because with redux we observe the state as a whole, new state wil cause
     * the composable to recompositios or is compose smart enough?
     *
     */
    val state = store.state.collectAsState()

    SearchToolbar(query = state.value.query) { querySlice ->
        store.dispatch(SearchAction.AppendSearchQuery(querySlice))
    }

}