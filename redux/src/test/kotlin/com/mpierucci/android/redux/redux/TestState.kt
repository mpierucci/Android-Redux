package com.mpierucci.android.redux.redux

sealed class TestState {
    object Initial : TestState()
    object Dummy : TestState()
}