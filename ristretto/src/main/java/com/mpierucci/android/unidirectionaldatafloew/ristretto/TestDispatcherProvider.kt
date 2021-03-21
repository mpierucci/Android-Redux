package com.mpierucci.android.unidirectionaldatafloew.ristretto

import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher


@ExperimentalCoroutinesApi
class TestDispatcherProvider(private val testDispatcher: TestCoroutineDispatcher) :
    DispatcherProvider {
    override fun default() = testDispatcher
    override fun io() = testDispatcher
    override fun main() = testDispatcher
    override fun unconfined() = testDispatcher
}
