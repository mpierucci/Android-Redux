package com.mpierucci.android.redux.search.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mpierucci.android.redux.search.R

@Composable
internal fun EmptyResultsSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .testTag("emptyResults")

    ) {
        Image(
            painter = painterResource(id = R.drawable.no_drinks),
            contentDescription = null,
            modifier = Modifier.size(150.dp),
            colorFilter = ColorFilter.tint(
                MaterialTheme.colors.onSurface
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Text(
            text = stringResource(id = R.string.search_start_searching_description),
            color = MaterialTheme.colors.onBackground
        )
    }
}
