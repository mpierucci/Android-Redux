package com.mpierucci.android.redux.core.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UrlModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://www.thecocktaildb.com/"
}