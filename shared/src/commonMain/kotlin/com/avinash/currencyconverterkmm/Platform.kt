package com.avinash.currencyconverterkmm

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient
