package com.avinash.currencyconverterkmm.repository.remote

import com.avinash.currencyconverterkmm.repository.model.CurrencyRate
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


class RemoteDataSourceTest {


    private val currencyService: ApiService = mockk()
    private val currencyRate: CurrencyRate = mockk()
    private val apiResponse = flowOf(Result.success(currencyRate))

    @Test
    fun emitsTheSameResponseFromApiService() = runTest {
        val remoteDataSource = setTheApiCallForSuccessCase()
        val response = remoteDataSource.getCurrencyRate()
        assertEquals(response, apiResponse)
    }

    @Test
    fun callsTheApiCallWhenGetCurrencyRateIsRequested() = runTest {
        val remoteDataSource = setTheApiCallForSuccessCase()
        remoteDataSource.getCurrencyRate()
        coVerify { currencyService.getCurrencyRate() }
    }

    private fun setTheApiCallForSuccessCase(): RemoteDataSource {
        coEvery { currencyService.getCurrencyRate() } returns apiResponse
        return RemoteDataSource(currencyService)
    }

}
