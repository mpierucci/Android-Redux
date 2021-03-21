package com.mpierucci.android.unidirectionaldatafloew.ristretto

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class CoroutineTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    val dispatcherProvider = TestDispatcherProvider(testDispatcherRule.testDispatcher)

    val testDispatcher get() = testDispatcherRule.testDispatcher
}