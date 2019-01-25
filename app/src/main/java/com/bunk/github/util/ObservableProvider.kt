package com.bunk.github.util

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ObservableProvider {
    fun createObservableWithInterval(
        initialDelay: Long,
        period: Long,
        timeUnit: TimeUnit
    ): Observable<Long> {
        return Observable.interval(initialDelay, period, timeUnit)
    }
}