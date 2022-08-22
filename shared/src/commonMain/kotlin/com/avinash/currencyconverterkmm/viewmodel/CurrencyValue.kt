package com.avinash.currencyconverterkmm.viewmodel

data class CurrencyValue(
    val name: String = USD,
    val value: Double = 1.0,
){
    companion object{
        const val USD = "USD"
    }
}