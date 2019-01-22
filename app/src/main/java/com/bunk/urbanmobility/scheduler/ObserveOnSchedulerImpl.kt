package com.bunk.urbanmobility.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers

class ObserveOnSchedulerImpl: ObserveOnScheduler {
    override val androidMainThreadScheduler = AndroidSchedulers.mainThread()
}