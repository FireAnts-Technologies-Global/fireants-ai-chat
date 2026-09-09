package com.pegas.aura.aigirlfriend.soul.data.network.model.auth

fun AuthSessionEnvelopeDto.requireData(): AuthSessionDto =
    data ?: error(message ?: "Missing auth session response data")

fun AuthUserEnvelopeDto.requireData(): AuthUserDto =
    data ?: error(message ?: "Missing auth user response data")
