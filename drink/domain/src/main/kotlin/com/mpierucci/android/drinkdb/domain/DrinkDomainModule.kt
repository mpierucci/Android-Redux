package com.mpierucci.android.drinkdb.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DrinkDomainModule {

    //TODO move to data module when ready.
    @Provides
    fun provideFakeRepository(): DrinkRepository {
        return object : DrinkRepository {
            override suspend fun getByName(drinkName: String): List<Drink> {
                return listOf(Drink("", "Margarita", "", "", "", "", emptyList()))
            }
        }
    }
}