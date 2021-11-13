package com.mpierucci.android.redux.ristretto

import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher


class TestDispatcherProvider(private val testDispatcher: TestCoroutineDispatcher) :
    DispatcherProvider {
    override fun default() = testDispatcher
    override fun io() = testDispatcher
    override fun main() = testDispatcher
    override fun unconfined() = testDispatcher
}
