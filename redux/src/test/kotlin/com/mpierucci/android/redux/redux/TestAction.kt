package com.mpierucci.android.redux.redux

sealed class TestAction {
    data class TestActionA(val dummyData:String): TestAction()
    object TestActionB : TestAction()
}