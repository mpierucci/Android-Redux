package com.mpierucci.android.redux.drink.presentation.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DrinkDetailScreen(
    drinkName: String
) {
    Text(text = "Information on $drinkName coming soon")
}