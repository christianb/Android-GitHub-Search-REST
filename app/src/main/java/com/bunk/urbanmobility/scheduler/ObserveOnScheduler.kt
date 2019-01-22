package com.bunk.urbanmobility.scheduler

import io.reactivex.Scheduler

interface ObserveOnScheduler {
    val androidMainThreadScheduler: Scheduler
}