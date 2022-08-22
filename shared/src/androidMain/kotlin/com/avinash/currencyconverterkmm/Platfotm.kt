package com.avinash.currencyconverterkmm

import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
}


actual val platformModule: Module = module {
    factory {
        val factory: Settings.Factory = AndroidSettings.Factory(context = androidContext())
        factory.create("currency_state")
    }
}

actual fun getCurrentMilliseconds() = System.currentTimeMillis()