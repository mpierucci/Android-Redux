package com.mpierucci.android.unidirectionaldataflow.search.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun SearchToolbarPreview() {
    SearchToolbar("",{})
}

@Composable
fun SearchToolbar(query: String, onQueryValueChanged: (String) -> Unit) {


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
                    // Toast.makeText(LocalContext.current, "Searched", Toast.LENGTH_SHORT).show()
                }
            )
        )


    }
}