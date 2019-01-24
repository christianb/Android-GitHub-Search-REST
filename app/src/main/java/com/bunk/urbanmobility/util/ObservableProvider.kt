package com.bunk.urbanmobility.util

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ObservableProvider {
    fun createObservableWithInterval(): Observable<Long> {
        return Observable.interval(5, 10, TimeUnit.SECONDS)
    }
}