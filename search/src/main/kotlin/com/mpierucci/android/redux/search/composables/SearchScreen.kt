package com.mpierucci.android.redux.search.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchStore


@ExperimentalComposeUiApi
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
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(drinks, { drink: Drink -> drink.id }) { drink ->

                    Row {
                        CoilImage(
                            data = drink.thumbnail,
                            contentDescription = null,
                            requestBuilder = {
                                size(150)
                                transformations(CircleCropTransformation())
                            }

                        )

                        Text(
                            text = drink.name,
                            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                            color = MaterialTheme.colors.onBackground
                        )
                    }
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
        Text(
            text = "Your  query yield  no results",
            color = MaterialTheme.colors.onBackground
        )
    }
}