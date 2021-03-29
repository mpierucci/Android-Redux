package com.mpierucci.android.redux.drink.data

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.drink.domain.Ingredient
import org.junit.Test
import com.mpierucci.android.redux.drink.domain.Drink as DomainDrink

class DrinkMappingTest {

    @Test
    fun `maps model into domain entity`() {
        val sut = Drink(
            "id",
            "name",
            "tags",
            null,
            "instructions",
            "thumb",
        )

        val expected = DomainDrink(
            "id",
            "name",
            "tags",
            null,
            "instructions",
            "thumb",
            emptyList()
        )

        val result = sut.toDomain()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `maps ingredients`() {
        val sut = Drink(
            "id",
            "name",
            "tags",
            null,
            "instructions",
            "thumb",
            ingredient1 = "ing",
            measure1 = "meas",
            ingredient15 = "ing15",
            measure15 = "meas15"
        )

        val expected = DomainDrink(
            "id",
            "name",
            "tags",
            null,
            "instructions",
            "thumb",
            listOf(
                Ingredient("ing", "meas"),
                Ingredient("ing15", "meas15")
            )
        )

        val result = sut.toDomain()

        assertThat(result).isEqualTo(expected)
    }


    @Test
    fun `does not map ingredient if ingredient value is null`() {
        val sut = Drink(
            "id",
            "name",
            "tags",
            null,
            "instructions",
            "thumb",
            ingredient1 = "ing",
            measure1 = "meas",
            ingredient15 = "ing15",
        )

        val expected = DomainDrink(
            "id",
            "name",
            "tags",
            null,
            "instructions",
            "thumb",
            listOf(Ingredient("ing", "meas"))
        )

        val result = sut.toDomain()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `does not map ingredient if measure value is null`() {
        val sut = Drink(
            "id",
            "name",
            "tags",
            null,
            "instructions",
            "thumb",
            ingredient1 = "ing",
            measure1 = "meas",
            measure15 = "meas15"
        )

        val expected = DomainDrink(
            "id",
            "name",
            "tags",
            null,
            "instructions",
            "thumb",
            listOf(Ingredient("ing", "meas"))
        )

        val result = sut.toDomain()

        assertThat(result).isEqualTo(expected)
    }
}