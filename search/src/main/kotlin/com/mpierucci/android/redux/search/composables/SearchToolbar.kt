package com.mpierucci.android.redux.search.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mpierucci.android.redux.search.R

@ExperimentalComposeUiApi
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SearchToolbarPreview() {
    SearchToolbar("", {}, {})
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchToolbarPreviewDark() {
    SearchToolbar("", {}, {})
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchToolbar(
    query: String,
    onQueryValueChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            value = query,
            onValueChange = {
                onQueryValueChanged(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearch(query)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),

            shape = RoundedCornerShape(26.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
}
