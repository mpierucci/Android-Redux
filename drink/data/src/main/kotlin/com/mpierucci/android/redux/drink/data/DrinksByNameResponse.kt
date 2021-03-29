package com.mpierucci.android.redux.drink.data

import kotlinx.serialization.Serializable

@Serializable
internal data class DrinksByNameResponse(val drinks: List<Drink>?)