package com.mpierucci.android.redux.drink.domain

sealed class DrinkError {
    object NoConnection: DrinkError()
    object Unknown: DrinkError()
}