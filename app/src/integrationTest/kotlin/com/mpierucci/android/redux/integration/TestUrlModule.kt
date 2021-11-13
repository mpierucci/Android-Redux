package com.mpierucci.android.redux.integration

import com.mpierucci.android.redux.core.data.BaseUrl
import com.mpierucci.android.redux.core.data.UrlModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UrlModule::class]
)
object TestUrlModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "http://127.0.0.1:8080"
}