package com.mpierucci.android.redux.search.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchState
import com.mpierucci.android.redux.search.SearchStore


@Composable
fun SearchScreen(
    store: SearchStore
) {
    val state by store.state.collectAsState()
    SearchScreen(
        state = state,
        onQueryChanged = { querySlice ->
            store.dispatch(SearchAction.AppendSearchQuery(querySlice))
        },
        onSearch = { searchQuery ->
            store.dispatch(SearchAction.Search(searchQuery))
        },
        onDrinkTapped = { drinkName->
            store.dispatch(SearchAction.NavigateToDrinkDetail(drinkName))
        }
    )
}

@Composable
internal fun SearchScreen(
    state: SearchState,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    onDrinkTapped: (String) -> Unit
) {
    val drinks = state.drinks
    Column {
        SearchToolbar(
            query = state.query,
            onQueryValueChanged = onQueryChanged,
            onSearch = onSearch
        )

        if (state.loading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("searchProgress")
            )
        }

        when {

            state.error != null -> {
                ErrorScreen(error = state.error)
            }

            drinks == null -> {
                StartSearchingSection()
            }

            drinks.isEmpty() -> {
                EmptyResultsSection()
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("drinkList"),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(drinks, { drink: Drink -> drink.id }) { drink ->
                        Card(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .clickable {
                                    onDrinkTapped(drink.name)
                                },
                            shape = RoundedCornerShape(3.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Image(
                                    painter = rememberImagePainter(drink.thumbnail),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                )

                                Column(
                                    Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
                                ) {
                                    Text(
                                        text = drink.name,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = drink.tags,
                                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}





