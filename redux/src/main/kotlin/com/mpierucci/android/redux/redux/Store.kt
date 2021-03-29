package com.mpierucci.android.redux.redux

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.compose
import com.mpierucci.android.redux.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

