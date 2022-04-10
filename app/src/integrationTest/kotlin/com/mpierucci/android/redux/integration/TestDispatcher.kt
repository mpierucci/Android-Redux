package com.mpierucci.android.redux.integration


import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

object TestDispatcher {
    private val scheduler = TestCoroutineScheduler()
    val testCoroutineDispatcher = UnconfinedTestDispatcher(scheduler)
}