package com.avinash.currencyconverterkmm.repository.local

import com.avinash.currencyconverterkmm.getCurrentMilliseconds
import com.avinash.currencyconverterkmm.repository.model.CurrencyRate
import com.russhwolf.settings.Settings
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class LocalDataSource(private val settings: Settings) {

    companion object {
        private const val CURRENCY_RATE = "currency_rate"
        private const val LAST_MODIFIED_TIME = "last_modified_time"
    }

    fun saveCurrency(currencyRate: CurrencyRate) {
        settings.putString(CURRENCY_RATE, Json.encodeToString(currencyRate))
        settings.putLong(LAST_MODIFIED_TIME, getCurrentMilliseconds())
    }

    fun getCurrency(): CurrencyRate = settings.getStringOrNull(CURRENCY_RATE)?.let { currencyRate ->
        Json.decodeFromString(currencyRate)
    } ?: CurrencyRate()


    fun saveCurrencyRateLmt(lmt: Long) {
        settings.putLong(LAST_MODIFIED_TIME, lmt)
    }

    fun getLmtForRate() = settings.getLong(LAST_MODIFIED_TIME, -1)
}