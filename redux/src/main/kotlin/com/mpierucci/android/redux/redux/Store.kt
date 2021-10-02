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
        .reduceOrNull { function, acc -> acc compose function }

    abstract suspend fun reduce(previous: State, action: Action): State

    @MainThread
    fun dispatch(action: Action) {

        /*
            Todo check:
            this open a new coroutine each time is called, si uf middlewwares or other actions are dispacjted coul cause race condition?
            should this be a shared flow being collected? what happens middleware start bacgroun work, would this suspend the flow snd cause bygs (delayin future  actins)
         */
        viewModelScope.launch(dispatcherProvider.main()) {
            val previousState = _state.value
            val interceptedAction = chain?.invoke(action) ?: action
            val newState = reduce(previousState, interceptedAction)

            if (newState != previousState) _state.emit(newState)
        }
    }
}

