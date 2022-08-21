package com.avinash.currencyconverterkmm.repository.local

import com.avinash.currencyconverterkmm.repository.model.Rate
import kotlinx.coroutines.flow.flow


class LocalDataSource{

    fun getRate() = flow { emit(Rate.defaultRate()) }

    suspend fun saveRate(rate: Map<String, Double>) {
       // dataStore.saveRate(rate = rate)
    }

    fun getLmtForRate() = Unit//dataStore.getLmtOFRate()
}