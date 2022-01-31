package com.mpierucci.android.redux.navigation

import com.mpierucci.android.redux.navigation.NavigationCommand
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {

    /*
        Why channel..https://elizarov.medium.com/shared-flows-broadcast-channels-899b675e805c
        there should be only one receiver which is in our root composable
     */
    private val _commands = Channel<NavigationCommand>()

    val commands: Flow<NavigationCommand> = _commands.receiveAsFlow()

    suspend fun navigate(navigationCommand: NavigationCommand) {
        _commands.send(navigationCommand)
    }
}