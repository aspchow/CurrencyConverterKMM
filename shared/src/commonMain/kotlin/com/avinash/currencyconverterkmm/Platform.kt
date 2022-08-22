package com.avinash.currencyconverterkmm

import com.russhwolf.settings.Settings
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.koin.core.module.Module

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient

expect val platformModule : Module

expect fun getCurrentMilliseconds() : Long