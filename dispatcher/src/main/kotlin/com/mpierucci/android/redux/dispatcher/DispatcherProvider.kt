package com.mpierucci.android.redux.dispatcher

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface  DispatcherProvider {

    fun main(): CoroutineDispatcher = Dispatchers.Main.immediate

    fun default(): CoroutineDispatcher = Dispatchers.Default

    fun io(): CoroutineDispatcher = Dispatchers.IO

    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class ReleaseDispatcherProvider @Inject constructor() : DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
    @Binds
    abstract fun bindReleaseDispatcherProvider(provider: ReleaseDispatcherProvider): DispatcherProvider
}