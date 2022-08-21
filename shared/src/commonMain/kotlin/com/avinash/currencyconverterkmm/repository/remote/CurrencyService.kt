package com.avinash.currencyconverterkmm.repository.remote

import com.avinash.currencyconverterkmm.httpClient
import com.avinash.currencyconverterkmm.repository.model.CurrencyRate
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.serialization.json.Json

class ApiService(
) {


    suspend fun getCurrencyRate(): Flow<Result<CurrencyRate>> =
        request(endPoint = "/api/latest.json") {
            addParam("app_id", "d174b30ba60740d4a5beb8c09de1f701")
        }


    private val httpClient = httpClient {
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.SIMPLE
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                encodeDefaults = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    private suspend inline fun <reified T> request(
        requestType: HttpMethod = HttpMethod.Get,
        endPoint: String,
        crossinline addParams: StringBuilder.() -> Unit = {},
    ) =
        flow<Result<T>> {
            val urlBuilder =
                StringBuilder("https://openexchangerates.org").append(endPoint).append("?")
            urlBuilder.addParams()
            emit(Result.success(httpClient.request {
                url(urlBuilder.toString())
                method = requestType
            }.body()))
        }.retryWhen { cause, _ ->
            cause.printStackTrace()
            cause is NullPointerException || cause is ServerResponseException
        }.catch { error ->
            emit(Result.failure(error))
        }


    private fun StringBuilder.addParam(param: String, value: String) {
        append("&$param").append("=$value")
    }
}