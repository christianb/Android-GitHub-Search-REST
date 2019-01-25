package com.bunk.github.scheduler

import io.reactivex.Scheduler

interface ObserveOnScheduler {
    val androidMainThreadScheduler: Scheduler
}