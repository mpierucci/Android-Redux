package com.mpierucci.android.redux.redux.experimental


import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.ristretto.CoroutineTest
import com.mpierucci.android.redux.ristretto.any
import com.mpierucci.android.redux.ristretto.eq
import com.mpierucci.android.redux.redux.TestAction
import com.mpierucci.android.redux.redux.TestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class StoreTest : CoroutineTest() {

    @Test
    fun `initial state is emitted`() {
        val sut = DummyStore(emptyList())

        val result = sut.state.value

        assertThat(result).isEqualTo(TestState.Initial)
    }

    @Test
    fun `emits state after reduction`() {
        val sut = DummyStore(emptyList())

        sut.dispatch(TestAction.TestActionA(""))

        val result = sut.state.value

        assertThat(result).isEqualTo(TestState.Dummy)
    }

    @Test
    fun `reduces original action if no middleware are provided`() = testDispatcher.runBlockingTest {

        val sut = DummyStore(emptyList())

        val sutSpy = Mockito.spy(sut)
        sutSpy.dispatch(TestAction.TestActionA("sut"))

        Mockito.verify(sutSpy).reduce(any(), eq(TestAction.TestActionA("sut")))
    }

    @Test
    fun `apply middlewares before reducing state`() = testDispatcher.runBlockingTest {
        val middleware: Middleware<TestState, TestAction> = { _ ->
            { action ->
                when (action) {
                    is TestAction.TestActionA -> TestAction.TestActionB
                    else -> action
                }
            }
        }
        val sut = DummyStore(listOf(middleware))

        val sutSpy = Mockito.spy(sut)
        sutSpy.dispatch(TestAction.TestActionA(""))

        Mockito.verify(sutSpy).reduce(any(), eq(TestAction.TestActionB))
    }


    private inner class DummyStore(
        middleware: List<Middleware<TestState, TestAction>>
    ) : Store<TestState, TestAction>(
        TestState.Initial,
        middleware,
        dispatcherProvider
    ) {
        override suspend fun reduce(previous: TestState, action: TestAction): TestState {
            return when (action) {
                is TestAction.TestActionA -> TestState.Dummy

                is TestAction.TestActionB -> TestState.Initial
            }
        }
    }
}