package com.mpierucci.android.redux.drink.domain

/*
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

 */