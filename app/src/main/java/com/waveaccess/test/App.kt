package com.waveaccess.test

import android.app.Application
import com.waveaccess.test.di.ApplicationComponent
import com.waveaccess.test.di.DaggerApplicationComponent
import com.waveaccess.test.di.DataBaseModule

class App: Application() {
    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
            .dataBaseModule(DataBaseModule(this))
            .build()
    }
}