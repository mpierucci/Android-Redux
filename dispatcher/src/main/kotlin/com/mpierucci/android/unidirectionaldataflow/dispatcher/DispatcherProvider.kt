package com.mpierucci.android.unidirectionaldataflow.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

interface DispatcherProvider {

    fun main(): CoroutineDispatcher = Dispatchers.Main.immediate

    fun default(): CoroutineDispatcher = Dispatchers.Default

    fun io(): CoroutineDispatcher = Dispatchers.IO

    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}


@ExperimentalCoroutinesApi
class TestDispatcherProvider(private val testDispatcher: TestCoroutineDispatcher) :
    DispatcherProvider {
    override fun default() = testDispatcher
    override fun io() = testDispatcher
    override fun main() = testDispatcher
    override fun unconfined() = testDispatcher
}

class ReleaseDispatcherProvider : DispatcherProvider

/*

  For later




@Module
abstract class DispatcherModule {

    @Binds
    @Singleton
    abstract fun bindDispatcherProvider(dispatcher: ReleaseDispatcher): DispatcherProvider
}
 */