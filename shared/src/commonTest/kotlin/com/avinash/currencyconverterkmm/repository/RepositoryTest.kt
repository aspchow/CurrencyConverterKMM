package com.avinash.currencyconverterkmm.repository

import com.avinash.currencyconverterkmm.repository.local.LocalDataSource
import com.avinash.currencyconverterkmm.repository.model.CurrencyRate
import com.avinash.currencyconverterkmm.repository.remote.ApiRefreshChecker
import com.avinash.currencyconverterkmm.repository.remote.RemoteDataSource
import io.mockk.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class RepositoryTest {

    private val apiRefreshChecker: ApiRefreshChecker = mockk()
    private val localDataSource: LocalDataSource = mockk()
    private val remoteDataSource: RemoteDataSource = mockk()
    private val currencyRate : CurrencyRate = mockk()
    private val lmtValue = 1L
    private val apiSuccessCase = flowOf(Result.success(currencyRate))
    private val apiFailureCase: Flow<Result<CurrencyRate>> = flowOf(Result.failure(Exception("")))


    @Test
    fun shouldCallTheApiRefresherOnCurrencyRequest() = runTest{
        val repository = getRepositoryOnLmtTimePassed(isApiSuccess = true)
        repository.getCurrencyRateFromServer().first()
        coVerify { apiRefreshChecker.checkIfTimePassed(lmtValue) }

    }


    @Test
    fun shouldCallTheServerForResponseIfTimePassed() = runTest{
        val repository = getRepositoryOnLmtTimePassed(isApiSuccess = true)
        repository.getCurrencyRateFromServer().first()
        coVerify { remoteDataSource.getCurrencyRate() }

    }


    @Test
    fun shouldNotCallTheServerForResponseIfTimeNotPassed() = runTest{
        val repository = getRepositoryOnLmtTimeNotPassed()
        repository.getCurrencyRateFromServer().first()
        coVerify { remoteDataSource.getCurrencyRate() wasNot called }

    }

    @Test
    fun emitsTheSuccessFlowIfTheLmtTimeNotPassed() = runTest{
        val repository = getRepositoryOnLmtTimeNotPassed()
        val response = repository.getCurrencyRateFromServer().first()
        assertTrue(response.isSuccess)
    }

    @Test
    fun emitsTheSuccessFlowIfTheLmtTimePassedAndApiIsSuccessful() = runTest{
        val repository = getRepositoryOnLmtTimePassed(isApiSuccess = true)
        val response = repository.getCurrencyRateFromServer().first()
        assertTrue(response.isSuccess)
    }

    @Test
    fun emitsTheFailureFlowIfTheLmtTimePassedAndApiIsFailure() = runTest{
        val repository = getRepositoryOnLmtTimePassed(isApiSuccess = false)
        val response = repository.getCurrencyRateFromServer().first()
        assertFalse(response.isSuccess)
    }

    private fun getRepositoryOnLmtTimeNotPassed(): Repository {

        every { localDataSource.getCurrency() } returns currencyRate

        every { localDataSource.getLmtForRate() } returns lmtValue

        every { apiRefreshChecker.checkIfTimePassed(lmtValue) } returns false

        return Repository(localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            apiRefreshChecker = apiRefreshChecker)
    }

    private fun getRepositoryOnLmtTimePassed(isApiSuccess : Boolean): Repository {

        every { localDataSource.getCurrency() } returns currencyRate

        every { localDataSource.saveCurrency(currencyRate = currencyRate) } returns Unit

        every { localDataSource.getLmtForRate() } returns lmtValue

        every { apiRefreshChecker.checkIfTimePassed(lmtValue) } returns true

        coEvery { remoteDataSource.getCurrencyRate() } returns if (isApiSuccess) apiSuccessCase else apiFailureCase


                return Repository(localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            apiRefreshChecker = apiRefreshChecker)
    }
}