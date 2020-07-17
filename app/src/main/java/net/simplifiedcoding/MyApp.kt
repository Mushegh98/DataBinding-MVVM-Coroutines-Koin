package net.simplifiedcoding

import android.app.Application
import net.simplifiedcoding.di.appModule
import net.simplifiedcoding.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewModelModule))
        }
    }

}