package com.mpierucci.android.unidirectionaldataflow.redux.experimental


/**
 * From the reduxjs docs:
 * Reducers specify how the application's state changes in response to actions sent to the store.
 */
typealias Reducer<State, Action> = (State, Action) -> State

/**
 * A middleware is a higher-order function that composes a dispatch function to return a new dispatch function.
 */
typealias Middleware<State, Action> = (Store<State, Action>) -> (Action) -> Action