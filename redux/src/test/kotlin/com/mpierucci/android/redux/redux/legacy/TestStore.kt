package com.mpierucci.android.redux.redux.legacy

import arrow.core.Either
import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import com.mpierucci.android.redux.redux.TestAction
import com.mpierucci.android.redux.redux.TestEffect
import com.mpierucci.android.redux.redux.TestState

class TestStore : Store<TestState, TestAction, TestEffect>(
    TestState.Initial,
    emptyList(),
    object : DispatcherProvider {}
) {

    override suspend fun reduce(
        previous: TestState,
        action: TestAction
    ): Either<TestEffect, TestState> {
        TODO("Not yet implemented")
    }
}