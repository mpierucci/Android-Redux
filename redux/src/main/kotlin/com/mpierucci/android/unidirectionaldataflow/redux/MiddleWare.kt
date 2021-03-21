package com.mpierucci.android.unidirectionaldataflow.redux


/*
    Sources: https://jayrambhia.com/blog/android-redux-middleware
 */


typealias Middleware<Action> = (Store<*, Action, *>, Action, DispatchChain<Action>) -> Action

typealias DispatchChain<Action> = (Store<*, Action, *>, Action) -> Action



