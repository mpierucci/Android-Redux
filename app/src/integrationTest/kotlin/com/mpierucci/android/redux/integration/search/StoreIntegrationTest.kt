package com.mpierucci.android.redux.integration.search

import androidx.activity.viewModels
import com.mpierucci.android.redux.integration.MainActivityRule
import com.mpierucci.android.redux.integration.TestDispatcher
import com.mpierucci.android.redux.redux.Store
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
internal abstract class StoreIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val coroutineRule = CoroutineTestDispatcherRule(TestDispatcher.testCoroutineDispatcher)

    @get:Rule(order = 2)
    val mainActivityRule = MainActivityRule()

    /*
    As per the official documentation, we need a handle to an activity ( with @AndroidEntryPoint)
    to get activity  ( or view models for this case ) bindings --Add to doc
   */
    inline fun <reified T : Store<*, *>> getStore(): T {
        val controller = mainActivityRule.controller
        val store by controller.get().viewModels<T>()
        return store
    }
}