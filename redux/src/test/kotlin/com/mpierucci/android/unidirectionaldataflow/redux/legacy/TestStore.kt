package com.mpierucci.android.unidirectionaldataflow.redux.legacy

import arrow.core.Either
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import com.mpierucci.android.unidirectionaldataflow.redux.TestAction
import com.mpierucci.android.unidirectionaldataflow.redux.TestEffect
import com.mpierucci.android.unidirectionaldataflow.redux.TestState

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