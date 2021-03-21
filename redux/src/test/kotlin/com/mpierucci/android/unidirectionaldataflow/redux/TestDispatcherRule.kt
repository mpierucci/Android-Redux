package com.mpierucci.android.unidirectionaldataflow.redux

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

//TODO find how best to share this across modules
@ExperimentalCoroutinesApi
class TestDispatcherRule : TestRule, TestWatcher() {
    val testDispatcher = TestCoroutineDispatcher()

    override fun finished(description: Description?) {
        testDispatcher.cleanupTestCoroutines()
    }
}