package com.mpierucci.android.unidirectionaldataflow.redux


/*
    Sources: https://jayrambhia.com/blog/android-redux-middleware
 */
fun interface DispatchChain<Action> {
    fun next(store: Store<*, Action, *>, action: Action): Action?
}

fun interface Middleware<Action> {
    fun apply(store: Store<*, Action, *>, action: Action, next: DispatchChain<Action>): Action?
}

internal class NextMiddleware<Action>(
    private val middleware: Middleware<Action>,
    private val next: DispatchChain<Action>
) : DispatchChain<Action> {
    override fun next(store: Store<*, Action, *>, action: Action): Action? {
        return middleware.apply(store, action, next)
    }
}

internal class EndOfChain<Action> : DispatchChain<Action> {
    override fun next(store: Store<*, Action, *>, action: Action) = action
}


