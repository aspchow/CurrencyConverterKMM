package com.avinash.currencyconverterkmm.repository

import com.avinash.currencyconverterkmm.repository.local.LocalDataSource
import com.avinash.currencyconverterkmm.repository.model.CurrencyRate
import com.avinash.currencyconverterkmm.repository.remote.ApiRefreshChecker
import com.avinash.currencyconverterkmm.repository.remote.RemoteDataSource
import com.avinash.currencyconverterkmm.utils.handle
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiRefreshChecker: ApiRefreshChecker
) {

    fun getRate() = localDataSource.getRate()

    fun getCurrencyRateFromServer() = flow<Result<Unit>> {
        if (apiRefreshChecker.checkIfTimePassed(1 ).not()) {//localDataSource.getLmtForRate().first()).not()) {
            emit(Result.success(Unit))
            return@flow
        }
        val result: Result<CurrencyRate> = remoteDataSource.getCurrencyRate().first()
        result.handle(
            onSuccess = {
                localDataSource.saveRate(it.rates)
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
