package com.mpierucci.android.redux.redux.legacy


/*
    Sources: https://jayrambhia.com/blog/android-redux-middleware
 */


typealias Middleware<Action> = (Store<*, Action, *>, Action, DispatchChain<Action>) -> Action

typealias DispatchChain<Action> = (Store<*, Action, *>, Action) -> Action



