package com.avinash.currencyconverterkmm.viewmodel

data class CurrencyValue(
    val name: String,
    val value: Double,
){
    companion object{
        const val USD = "USD"
    }
}