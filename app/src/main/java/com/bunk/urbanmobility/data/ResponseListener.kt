package com.bunk.urbanmobility.data

interface ResponseListener<T> {
    fun onSuccess(t: T)
    fun onError(throwable: Throwable)
}
