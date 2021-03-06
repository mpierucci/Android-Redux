package com.mpierucci.android.redux.redux


import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.redux.TestAction.TestActionA
import com.mpierucci.android.redux.redux.TestState.*
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class StoreTest {

    @get:Rule
    val coroutineRule = CoroutineTestDispatcherRule()


    @Test
    fun `initial state is emitted`() {
        val sut = DummyStore(emptyList())

        val result = sut.state.value

        assertThat(result).isEqualTo(Initial)
    }

    @Test
    fun `emits state after reduction`() {
        val sut = DummyStore(emptyList())

        sut.dispatch(TestActionA(""))

        val result = sut.state.value

        assertThat(result).isEqualTo(StateA)
    }

    @Test
    fun `apply middlewares before reducing state`() = runTest {
        val middleware: Middleware<TestState, TestAction> = { _ ->
            { action ->
                when (action) {
                    is TestActionA -> TestAction.TestActionB
                    else -> action
                }
            }
        }
        val sut = DummyStore(listOf(middleware))

        sut.dispatch(TestActionA(""))

        val result = sut.state.value

        assertThat(result).isEqualTo(StateB)
    }

    @Test
    fun `does not duplicate states`() = runTest {
        val sut = DummyStore(emptyList())
        val states = mutableListOf<TestState>()

        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.toList(states)
        }

        sut.dispatch(TestActionA(""))
        sut.dispatch(TestActionA(""))

        assertThat(states).hasSize(2)

        job.cancel()
    }


    private inner class DummyStore(
        middleware: List<Middleware<TestState, TestAction>>
    ) : Store<TestState, TestAction>(
        Initial,
        middleware,
        coroutineRule.testDispatcherProvider
    ) {
        override suspend fun reduce(previous: TestState, action: TestAction): TestState {
            return when (action) {
                is TestActionA -> StateA

                is TestAction.TestActionB -> StateB
            }
        }
    }
}