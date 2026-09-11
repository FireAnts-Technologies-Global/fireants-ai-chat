package com.pegas.yuki.virtual.chat.domain.model.common

sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>

    data class Failure(val error: PublicError) : AppResult<Nothing>
}
