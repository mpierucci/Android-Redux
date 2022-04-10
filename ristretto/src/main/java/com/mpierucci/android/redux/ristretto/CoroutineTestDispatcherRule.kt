package com.mpierucci.android.redux.ristretto

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutineTestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestRule, TestWatcher() {

    val testDispatcherProvider: TestDispatcherProvider
        get() = TestDispatcherProvider(testDispatcher)

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}
