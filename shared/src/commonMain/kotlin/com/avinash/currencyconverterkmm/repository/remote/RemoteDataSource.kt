package com.avinash.currencyconverterkmm.repository.remote


class RemoteDataSource(
    private val service: ApiService
) {
    suspend fun getCurrencyRate() = service.getCurrencyRate()
}