package com.mpierucci.android.redux.ristretto

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TestDispatcherRule : TestRule, TestWatcher() {
    val testDispatcher = TestCoroutineDispatcher()

    override fun finished(description: Description?) {
        testDispatcher.cleanupTestCoroutines()
    }
}