package com.mpierucci.android

import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class ReleaseDispatcherProvider @Inject constructor() : DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
    @Binds
    abstract fun bindReleaseDispatcherProvider(provider: ReleaseDispatcherProvider): DispatcherProvider

}