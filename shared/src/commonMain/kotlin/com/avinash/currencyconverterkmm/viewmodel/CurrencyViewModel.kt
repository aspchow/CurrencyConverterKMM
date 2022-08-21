package com.avinash.currencyconverterkmm.viewmodel

import com.avinash.currencyconverterkmm.repository.Repository
import com.avinash.currencyconverterkmm.repository.model.Currency
import com.avinash.currencyconverterkmm.repository.model.CurrencyRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class CurrencyViewModel(
    private val repository: Repository,
) {

    private val _selectedCurrency = MutableStateFlow(CurrencyValue(CurrencyValue.USD, 1.0))
    val selectedCurrency: MutableStateFlow<CurrencyValue> = _selectedCurrency

    private val _searchedCurrency = MutableStateFlow("")

    val searchedCurrency: StateFlow<String> = _searchedCurrency

    val currencies = flow {
        emit(CurrencyRate())
    }

    fun getCurrencyRate() = repository.getRate()

    fun getCurrencyRateFromServer() =
        repository.getCurrencyRateFromServer().flowOn(Dispatchers.Default)

    @OptIn(ExperimentalCoroutinesApi::class)
    val availableCurrencies: Flow<List<String>> = currencies.flatMapLatest { currencyRate ->
        _searchedCurrency.flatMapLatest { search ->
            _selectedCurrency.map { selectedCurrency ->
                if (search.isEmpty()) {
                    currencyRate.rates.keys.toList()
                } else {
                    currencyRate.rates.keys.toList().filter { currency ->
                        currency.contains(search,
                            ignoreCase = true) && currency != selectedCurrency.name
                    }
                }
            }
        }
    }

    fun setTheCurrency(currency: CurrencyValue) {
        _selectedCurrency.value = currency
    }

    fun search(query: String) {
        _searchedCurrency.value = query
    }
}