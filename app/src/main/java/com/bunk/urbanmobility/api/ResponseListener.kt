package com.bunk.urbanmobility.api

interface ResponseListener<T> {
    fun onSuccess(t: T)
    fun onError(throwable: Throwable)
}
