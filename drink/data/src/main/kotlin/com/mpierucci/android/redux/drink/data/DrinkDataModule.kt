package com.mpierucci.android.redux.drink.data

import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import com.mpierucci.android.redux.drink.domain.DrinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal object DrinkDataModule {

    @Provides
    fun provideDrinkApi(retrofit: Retrofit): DrinkApi = retrofit.create(DrinkApi::class.java)

    @Provides
    fun provideDrinkRepository(
        drinkApi: DrinkApi,
        dispatcherProvider: DispatcherProvider
    ): DrinkRepository = DrinkRepository(drinkApi, dispatcherProvider)
}