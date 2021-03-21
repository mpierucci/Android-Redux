package com.mpierucci.android.unidirectionaldataflow.redux

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.unidirectionaldataflow.dispatcher.TestDispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.redux.TestAction.TestActionA
import com.mpierucci.android.unidirectionaldataflow.redux.TestAction.TestActionB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StoreTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val dispatcherProvider = TestDispatcherProvider(testDispatcherRule.testDispatcher)

    private val testDispatcher get() = testDispatcherRule.testDispatcher

    @Test
    fun `initial state is emitted`() {
        val sut = DummyStore(emptyList())

        val result = sut.state.value

        assertThat(result).isEqualTo(TestState.Initial)
    }

    @Test
    fun `create middleware chain with middlewares`() {
        val middleware = Middleware<TestAction> { _, action, _ -> action }
        val sut = DummyStore(listOf(middleware))

        val result = sut.middleWareChain

        assertThat(result).isInstanceOf(NextMiddleware::class.java)
    }

    @Test
    fun `create middleware chain without  middlewares`() {

        val sut = DummyStore(emptyList())

        val result = sut.middleWareChain

        assertThat(result).isInstanceOf(EndOfChain::class.java)
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


    fun searchMiddleWare() = Middleware<TestAction> { store, action, next ->
        when (action) {
            is TestActionB -> {
                store.dispatch(TestActionA(""))
                null
            }
            else -> next.next(store, action)
        }
    }
}