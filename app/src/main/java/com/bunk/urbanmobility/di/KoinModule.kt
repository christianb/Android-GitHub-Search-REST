package com.bunk.urbanmobility.di

import android.content.Context
import android.location.LocationManager
import com.bunk.urbanmobility.scheduler.ObserveOnScheduler
import com.bunk.urbanmobility.scheduler.ObserveOnSchedulerImpl
import com.bunk.urbanmobility.scheduler.SubscribeOnScheduler
import com.bunk.urbanmobility.scheduler.SubscribeOnSchedulerImpl
import org.koin.android.ext.koin.androidContext

val module = org.koin.dsl.module.module {

//    module("api") {
//    }

    scope(ACTIVITY_SCOPE) { androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    factory<ObserveOnScheduler> { ObserveOnSchedulerImpl() }
    factory<SubscribeOnScheduler> { SubscribeOnSchedulerImpl() }
}

const val ACTIVITY_SCOPE = "activity_scope"