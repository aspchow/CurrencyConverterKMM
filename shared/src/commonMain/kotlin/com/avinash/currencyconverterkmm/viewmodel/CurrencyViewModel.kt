package com.avinash.currencyconverterkmm.viewmodel

import com.avinash.currencyconverterkmm.repository.Repository
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

    fun getCurrencyRateFromServer() =
        repository.getCurrencyRateFromServer().flowOn(Dispatchers.Default)

    @OptIn(ExperimentalCoroutinesApi::class)
    val availableCurrencies: Flow<List<CurrencyValue>> =
        repository.getCurrencyRate.flatMapLatest { currencyRate ->
            _searchedCurrency.flatMapLatest { search ->
                _selectedCurrency.map { selectedCurrency ->
                    if (search.isEmpty()) {
                        currencyRate.rates.map { entry ->
                            CurrencyValue(entry.key, entry.value)
                        }
                    } else {
                        currencyRate.rates.map { entry ->
                            CurrencyValue(entry.key, entry.value)
                        }.filter { currency ->
                            currency.name.contains(search,
                                ignoreCase = true) && currency.name != selectedCurrency.name
                        }
                    }
                }
            }
        }.flowOn(Dispatchers.Default)


    fun setTheCurrency(currency: CurrencyValue) {
        search("")
        _selectedCurrency.value = currency
    }

    fun search(query: String) {
        _searchedCurrency.value = query
    }
}