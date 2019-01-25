package com.bunk.github.data

interface ResponseListener<T> {
    fun onSuccess(t: T)
    fun onError(throwable: Throwable)
}
