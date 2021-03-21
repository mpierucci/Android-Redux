package com.mpierucci.android.unidirectionaldataflow.redux

sealed class TestState {
    object Initial : TestState()
    object Dummy : TestState()
}