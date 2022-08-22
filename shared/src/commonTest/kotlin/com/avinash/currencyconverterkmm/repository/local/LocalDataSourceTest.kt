/*
package com.avinash.currencyconverterkmm.repository.local

import com.russhwolf.settings.Settings
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlin.test.Test


class LocalDataSourceTest {

    private val settings : Settings = mockk()


    @Test
    fun callTheDataStoreForTheRateFlow() = runBlocking {
        val localDataSource = getTheLocalDataStoreToGetRate()
        localDataSource.getCurrency()
        coVerify {  }
    }

    @Test
    fun getTheRateFromDataStoreAndEmit() = runTest {
        val localDataSource = getTheLocalDataStoreToGetRate()
        val rateFlow = localDataSource.getRate()
        Assert.assertEquals(rateFlow, rateFlowFromDataSource)
    }


    @Test
    fun callTheDataStoreForTheRateLmtFlow() = runTest {
        val localDataSource = getTheLocalDataStoreToGetRateLmt()
        localDataSource.getLmtForRate()
        coVerify { dataStore.getLmtOFRate() }
    }

    @Test
    fun getTheRateLmtFromDataStoreAndEmit() = runTest {
        val localDataSource = getTheLocalDataStoreToGetRate()
        val rateFlow = localDataSource.getRate()
        Assert.assertEquals(rateFlow, rateFlowFromDataSource)
    }

    @Test
    fun saveTheRateInTheDataStore() = runTest {
        val localDataSource = getTheLocalDataStoreToUpdateRateLmt()
        localDataSource.saveRate(rate = rate)
        coVerify {
            dataStore.saveRate(rate = rate)
        }
    }


    private fun getTheLocalDataStoreToGetRate(): LocalDataSource {
        every { settings.get() } returns rateFlowFromDataSource
        return LocalDataSource(dataStore = dataStore)
    }

    private fun getTheLocalDataStoreToGetRateLmt(): LocalDataSource {
        every { dataStore.getLmtOFRate() } returns rateLmtFromDataSource
        return LocalDataSource(dataStore = dataStore)
    }

    private fun getTheLocalDataStoreToUpdateRateLmt(): LocalDataSource {
        coEvery { dataStore.saveRate(rate = rate) } returns Unit
        return LocalDataSource(dataStore = dataStore)
    }

}*/
