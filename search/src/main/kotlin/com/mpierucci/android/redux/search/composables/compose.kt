package com.mpierucci.android.redux.search.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchStore


@Preview
@Composable
fun SearchToolbarPreview() {
    SearchToolbar("", {}, {})
}

@Composable
fun SearchToolbar(
    query: String,
    onQueryValueChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        TextField(
            value = query,
            onValueChange = {
                onQueryValueChanged(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            leadingIcon = { Icons.Filled.Search },
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                }
            )
        )
    }
}

@Composable
fun SearchScreen(store: SearchStore) {

    val state = store.state.collectAsState().value
    val drinks = state.drinks
    Column {
        SearchToolbar(
            query = state.query,
            onQueryValueChanged = { querySlice ->
                store.dispatch(SearchAction.AppendSearchQuery(querySlice))
            },
            { searchQuery ->
                store.dispatch(SearchAction.Search(searchQuery))
            })

        if (state.drinks.isEmpty()) {
            EmptySearch()
        } else {
            LazyColumn {
                items(drinks.size, { key -> drinks[key].id }) {
                    Text(drinks[it].name, color = Color.White)

                }
            }
        }
    }
}

@Composable
/*
Column is a inline function and thus not skippable by default recomposition
SO to make it skipable we create its own Composable. This might be a case of premature composition..
 */
fun EmptySearch() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        Log.e("Recomposition", "Column called")
        Text(
            text = "Your  query yield  no results",
            color = MaterialTheme.colors.onBackground
        )
    }
}