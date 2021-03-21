package com.mpierucci.android.unidirectionaldataflow.redux

import arrow.core.Either
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider

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