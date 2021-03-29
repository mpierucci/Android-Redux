package com.mpierucci.android.redux.redux.legacy

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.ristretto.CoroutineTest
import com.mpierucci.android.redux.ristretto.any
import com.mpierucci.android.redux.ristretto.eq
import com.mpierucci.android.redux.redux.TestAction
import com.mpierucci.android.redux.redux.TestAction.TestActionA
import com.mpierucci.android.redux.redux.TestAction.TestActionB
import com.mpierucci.android.redux.redux.TestEffect
import com.mpierucci.android.redux.redux.TestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    fun `apply middlewares before reducing state`() = testDispatcher.runBlockingTest {
        val middleware: Middleware<TestAction> = { _, action, _ ->
            when (action) {
                is TestActionA -> TestActionB
                else -> action
            }
        }
        val sut = DummyStore(listOf(middleware))

        val sutSpy = Mockito.spy(sut)
        sutSpy.dispatch(TestActionA(""))

        Mockito.verify(sutSpy).reduce(any(), eq(TestActionB))
    }

    @Test
    fun `reduces original action if no middleware are provided`() = testDispatcher.runBlockingTest {

        val sut = DummyStore(emptyList())

        val sutSpy = Mockito.spy(sut)
        sutSpy.dispatch(TestActionA("sut"))

        Mockito.verify(sutSpy).reduce(any(), eq(TestActionA("sut")))
    }

    @Test
    fun `emits state after reduction`() {
        val sut = DummyStore(emptyList())

        sut.dispatch(TestActionA(""))

        val result = sut.state.value

        assertThat(result).isEqualTo(TestState.Dummy)
    }

    @Test
    fun `emits view effect after reduction`() = testDispatcher.runBlockingTest {
        val sut = DummyStore(emptyList())

        val effects = mutableListOf<TestEffect>()
        val job = launch {
            sut.effect.collect {
                effects.add(it)
            }
        }

        sut.dispatch(TestActionB)

        val result = effects.first()

        assertThat(result).isEqualTo(TestEffect.DummyEffect)

        job.cancel()
    }

    private inner class DummyStore(
        middleware: List<Middleware<TestAction>>
    ) : Store<TestState, TestAction, TestEffect>(
        TestState.Initial,
        middleware,
        dispatcherProvider
    ) {
        override suspend fun reduce(
            previous: TestState,
            action: TestAction
        ): Either<TestEffect, TestState> {
            return when (action) {
                is TestActionA -> Either.state(TestState.Dummy)
                is TestActionB -> Either.effect(TestEffect.DummyEffect)
            }
        }
    }


}