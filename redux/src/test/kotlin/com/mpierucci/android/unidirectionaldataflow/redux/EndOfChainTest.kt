package com.mpierucci.android.unidirectionaldataflow.redux

import com.google.common.truth.Truth.assertThat
import org.junit.Test


internal class EndOfChainTest {

    @Test
    fun `returns unmodified action`() {
        val store = TestStore()
        val action = TestAction.TestActionB
        val sut = EndOfChain<TestAction>()

        val result = sut.next(store, action)

        assertThat(result).isEqualTo(action)
    }
}