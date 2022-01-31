package com.mpierucci.android.redux.search.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.mpierucci.android.redux.drink.domain.DrinkError
import com.mpierucci.android.redux.search.R

@Composable
fun ErrorScreen(error: DrinkError) {
    val message = when (error) {
        is DrinkError.Unknown -> stringResource(id = R.string.search_unknown_error)

        is DrinkError.NoConnection -> stringResource(id = R.string.search_no_connection_error)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("errorScreen")
    ) {
        Text(text = message)
    }
}