package com.mpierucci.android.unidirectionaldataflow.redux

sealed class TestAction {
    data class TestActionA(val dummyData:String): TestAction()
    object TestActionB : TestAction()
}