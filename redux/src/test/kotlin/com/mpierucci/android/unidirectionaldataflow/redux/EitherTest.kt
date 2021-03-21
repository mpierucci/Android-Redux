package com.mpierucci.android.unidirectionaldataflow.redux

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EitherTest {

    @Test
    fun `state creates a right either`() {
        val result = Either.state("")

        assertThat(result.isRight()).isTrue()
    }

    @Test
    fun `effect creates a left either`() {
        val result = Either.effect("")

        assertThat(result.isLeft()).isTrue()
    }
}