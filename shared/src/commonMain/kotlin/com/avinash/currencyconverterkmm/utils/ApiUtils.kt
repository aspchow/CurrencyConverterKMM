package com.avinash.currencyconverterkmm.utils



suspend fun <T> Result<T>.handle(onSuccess: suspend (T) -> Unit, onFailure: suspend (String) -> Unit) {
    if (isSuccess) {
        onSuccess(getOrThrow())
    } else {
        onFailure(exceptionOrNull()?.message ?: "")
    }
}