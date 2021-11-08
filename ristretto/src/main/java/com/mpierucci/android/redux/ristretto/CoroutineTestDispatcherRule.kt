package com.mpierucci.android.redux.ristretto

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestDispatcherRule : TestRule, TestWatcher() {
    val testDispatcher = TestCoroutineDispatcher()

    val testDispatcherProvider: TestDispatcherProvider
    get() = TestDispatcherProvider(testDispatcher)

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        testDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}

@ExperimentalCoroutinesApi
fun CoroutineTestDispatcherRule.runBlockingTest(block: suspend TestCoroutineScope.()->Unit)
= testDispatcher.runBlockingTest { block() }