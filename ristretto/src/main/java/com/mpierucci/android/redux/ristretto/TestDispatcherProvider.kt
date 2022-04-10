package com.mpierucci.android.redux.ristretto

import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import kotlinx.coroutines.test.TestDispatcher


class TestDispatcherProvider(private val testDispatcher: TestDispatcher) :
    DispatcherProvider {
    override fun default() = testDispatcher
    override fun io() = testDispatcher
    override fun main() = testDispatcher
    override fun unconfined() = testDispatcher
}
