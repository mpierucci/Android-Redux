package com.mpierucci.android.redux.integration

import com.mpierucci.android.redux.dispatcher.DispatcherModule
import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import com.mpierucci.android.redux.ristretto.TestDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.test.TestCoroutineDispatcher


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherModule::class]
)
object TestDispatcherModule {

    @Provides
    fun provideTestDispatcherModule(): DispatcherProvider = TestDispatcherProvider(
        TestCoroutineDispatcher()
    )
}