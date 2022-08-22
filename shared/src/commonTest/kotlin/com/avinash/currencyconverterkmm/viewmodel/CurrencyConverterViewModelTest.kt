package com.avinash.currencyconverterkmm.viewmodel


import com.avinash.currencyconverterkmm.repository.Repository
import com.avinash.currencyconverterkmm.repository.model.CurrencyRate
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


class CurrencyConverterViewModelTest {


    private val repository: Repository = mockk()
    private val rateSuccessResponse: Flow<Result<Unit>> = flowOf(Result.success(Unit))
    private val currencyRate: CurrencyRate = mockk()
    private val localCurrencyFlow = MutableStateFlow(currencyRate)



    @Test
    fun emitsTheRateFlowObservedFromRepository() = runTest {
        val viewModel = getViewModel()
        val rateFlow = viewModel.getCurrencyRateFromServer()
        assertEquals(rateFlow.first(), rateSuccessResponse.first())
    }

    @Test
    fun requestsTheCurrencyRateFromServer() {
        val viewModel = getViewModel()
        viewModel.getCurrencyRateFromServer()
        verify { repository.getCurrencyRateFromServer() }
    }

    @Test
    fun emitsTheCurrencyRateFromServer() = runTest {
        val viewModel = getViewModel()
        val response = viewModel.getCurrencyRateFromServer()
       assertEquals(response.first(), rateSuccessResponse.first())
    }



    private fun getViewModel(): CurrencyViewModel {
        every { repository.getCurrencyRateFromServer() } returns rateSuccessResponse
        coEvery { repository.getCurrencyRate } returns localCurrencyFlow
                return CurrencyViewModel(repository = repository)
    }


}
