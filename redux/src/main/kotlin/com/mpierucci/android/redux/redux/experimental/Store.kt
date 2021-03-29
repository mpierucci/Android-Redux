package com.mpierucci.android.redux.redux.experimental

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.compose
import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * With a pure-ish (still no global application) you don't emmit effect/events to the view. You
 * model this through the event itself.
 *
 * To avoid  those events recreated on orientation changes you´d immediately send an action
 * to clear the that stat. But in order for this to work your presentation needs to support
 * partial updates otherwise  you´ll loose performance on redrawing a lot.
 *
 *
 * Not sure if we can achieve this with compose yet hence experimental APi.
 */
abstract class Store<State, Action>(
    initialState: State,
    middlewares: List<Middleware<State, Action>>,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> get() = _state.asStateFlow()

    private val chain = middlewares
        .map { middleware -> middleware(this) }
        .reduceOrNull { function, acc -> acc.compose(function) }

    abstract suspend fun reduce(previous: State, action: Action): State

    @MainThread
    fun dispatch(action: Action) {

        viewModelScope.launch(dispatcherProvider.main()) {
            val previousState = _state.value
            val interceptedAction = chain?.invoke(action) ?: action
            val newState = reduce(previousState, interceptedAction)

            if (newState != previousState) _state.emit(newState)
        }
    }
}

