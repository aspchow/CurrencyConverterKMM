package com.avinash.currencyconverterkmm.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRate(
    val rates: Map<String, Double> = mapOf(),
)