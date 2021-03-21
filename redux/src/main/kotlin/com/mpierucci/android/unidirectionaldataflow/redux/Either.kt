package com.mpierucci.android.unidirectionaldataflow.redux

import arrow.core.Either


/*
    Commodities for keep the convention of effect being left and State being right
 */
fun <State> Either.Companion.state(state: State): Either<Nothing, State> = Either.Right(state)
fun <Effect> Either.Companion.effect(effect: Effect): Either<Effect, Nothing> = Either.Left(effect)