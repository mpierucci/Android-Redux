package com.mpierucci.android.unidirectionaldataflow.drink.data

import kotlinx.serialization.Serializable

@Serializable
internal data class DrinksByNameResponse(val drinks: List<Drink>?)