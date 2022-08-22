package com.avinash.currencyconverterkmm

import com.russhwolf.settings.Settings
import io.ktor.client.*
import io.ktor.client.engine.ios.*
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Ios) {
    config(this)
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}

actual val platformModule: Module = module {

}

actual fun getCurrentMilliseconds(): Long = 1
