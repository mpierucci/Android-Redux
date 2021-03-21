package com.mpierucci.android.unidirectionaldataflow.redux

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.mpierucci.android.unidirectionaldataflow.dispatcher.DispatcherProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class Store<State, Action, Effect>(
    initialState: State,
    private val middlewares: List<Middleware<Action>>,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> get() = _state.asStateFlow()


    /*
        Why channel for events?
        SharedFlow is hot. This means that during periods where the is no observer, say during
        a configuration change, events emitted on to the flow are simply dropped. Regrettably,
        this also makes SharedFlow inappropriate to emit events on.

        Channels are used to handle events that must be processed exactly once. This happens in
        a design with a type of event that usually has a single subscriber, but intermittently
        (at startup or during some kind of reconfiguration) there are no subscribers at all, and
        there is a requirement that all posted events must be retained until a subscriber appears.

        With the shared flow, events are broadcast to an unknown number (zero or more)
        of subscribers. In the absence of a subscriber, any posted event is immediately dropped.
        It is a design pattern to use for events that must be processed immediately or not at all.

        With the channel, each event is delivered to a single subscriber. An attempt to post an
        event without subscribers will suspend as soon as the channel buffer becomes full, waiting
        for a subscriber to appear. Posted events are never dropped by default.

        Sources:
        https://elizarov.medium.com/shared-flows-broadcast-channels-899b675e805c
        https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055

     */
    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    abstract suspend fun reduce(previous: State, action: Action): Either<Effect, State>

    @MainThread
    fun dispatch(action: Action) {
        /*
            Note that we wont be using Mian.immediate anymore with this change. Since we are only
            going to emit into flows or channels this should not be a problem.

            Source :https://medium.com/@trionkidnapper/launching-a-kotlin-coroutine-for-immediate-execution-on-the-main-thread-8555e701163b
         */
        viewModelScope.launch(dispatcherProvider.main()) {

            val interceptedAction = applyMiddlewares(action) ?: return@launch
            reduce(state.value, interceptedAction).fold(
                { effect -> _effect.send(effect) },
                { state -> _state.emit(state) }
            )
        }
    }

    private fun applyMiddlewares(action: Action): Action? {
        return next(0)(this, action)
    }

    private fun next(index: Int): DispatchChain<Action> {
        if (index == middlewares.size) {
            return { _, action -> action }
        }

        return { store, action -> middlewares[0](store, action, next(index + 1)) }
    }
}