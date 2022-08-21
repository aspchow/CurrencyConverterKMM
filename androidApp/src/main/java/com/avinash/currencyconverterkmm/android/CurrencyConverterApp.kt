package com.avinash.currencyconverterkmm.android

import android.app.Application
import com.avinash.currencyconverterkmm.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class CurrencyConverterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CurrencyConverterApp)
            androidLogger()
        }
    }
}