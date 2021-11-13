package com.mpierucci.android.redux.integration

import com.mpierucci.android.MainActivity
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.robolectric.Robolectric.buildActivity
import org.robolectric.android.controller.ActivityController

/**
 * Crates  a MainActivity scenario through robolectric and controls its state.
 */
class MainActivityRule : TestRule, TestWatcher() {
    val controller: ActivityController<MainActivity> = buildActivity(MainActivity::class.java)


    override fun starting(description: Description) {
        controller.setup()
    }

    override fun finished(description: Description?) {
        controller.destroy()
    }
}

