package com.mpierucci.android.unidirectionaldataflow.redux

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.unidirectionaldataflow.redux.TestAction.TestActionA
import com.mpierucci.android.unidirectionaldataflow.redux.TestAction.TestActionB
import org.junit.Test

class NextMiddlewareTest {
    @Test
    fun `returns action from middleware`() {
        val testStore = TestStore()
        val testAction = TestActionB

        val middleware: Middleware<TestAction> = Middleware { store, action, next ->
            when (action) {
                is TestActionB -> TestActionA("")
                else -> next.next(store, action)
            }
        }

        val sut = NextMiddleware(middleware, { _, _ -> throw IllegalAccessError() })

        val result = sut.next(testStore, testAction)
        val expected = TestActionA("")

        assertThat(result).isEqualTo(expected)
    }
}