package com.hrb.holidays

import android.app.Application
import com.hrb.holidays.koindi.controllers.controllersModule
import com.hrb.holidays.koindi.databases.databasesModule
import com.hrb.holidays.koindi.interactors.interactorsModules
import com.hrb.holidays.koindi.presenters.presentersModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(presentersModule, databasesModule, interactorsModules, controllersModule)
        }
    }
}
