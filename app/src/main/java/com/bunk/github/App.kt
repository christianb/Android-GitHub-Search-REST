package com.bunk.github

import android.app.Application
import com.bunk.github.di.module
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(module))
    }
}