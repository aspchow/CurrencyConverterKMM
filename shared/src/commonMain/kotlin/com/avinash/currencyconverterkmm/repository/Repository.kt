package com.avinash.currencyconverterkmm.repository

import com.avinash.currencyconverterkmm.repository.local.LocalDataSource
import com.avinash.currencyconverterkmm.repository.model.CurrencyRate
import com.avinash.currencyconverterkmm.repository.remote.ApiRefreshChecker
import com.avinash.currencyconverterkmm.repository.remote.RemoteDataSource
import com.avinash.currencyconverterkmm.utils.handle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiRefreshChecker: ApiRefreshChecker,
) {

    val getCurrencyRate = MutableStateFlow(localDataSource.getCurrency())

    fun getCurrencyRateFromServer() = flow<Result<Unit>> {
        if (apiRefreshChecker.checkIfTimePassed(localDataSource.getLmtForRate()).not()) {
            emit(Result.success(Unit))
            return@flow
        }

        val result: Result<CurrencyRate> = remoteDataSource.getCurrencyRate().first()
        result.handle(
            onSuccess = {
                getCurrencyRate.value = it
                localDataSource.saveCurrency(it)
                emit(Result.success(Unit))
            },
            onFailure = {
                emit(Result.failure(Exception("something went wrong please try again")))
            }
        )
    }.catch {
        emit(Result.failure(Exception("something went wrong please try again")))
    }
}
