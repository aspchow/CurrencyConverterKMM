package com.avinash.currencyconverterkmm.android.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.avinash.currencyconverterkmm.android.R
import com.avinash.currencyconverterkmm.android.theme.*
import com.avinash.currencyconverterkmm.viewmodel.CurrencyValue
import com.avinash.currencyconverterkmm.viewmodel.CurrencyViewModel

@Composable
fun SelectCurrency(navController: NavController, viewModel: CurrencyViewModel) {


    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val availableCurrencies by viewModel.availableCurrencies.collectAsState(initial = emptyList())
    val searchedCurrency by viewModel.searchedCurrency.collectAsState()

    SelectCurrencyView(selectedCurrency = selectedCurrency,
        availableCurrencies = availableCurrencies,
        onRequestBack = {
            navController.popBackStack()
        },
        onCurrencySelected = { currency ->
            viewModel.setTheCurrency(currency = currency)
            navController.popBackStack()
        },
        searchQuery = searchedCurrency,
        onSearchChange = viewModel::search)
}


@Preview
@Composable
fun SelectCurrencyViewPreview() {
    SelectCurrencyView(selectedCurrency = CurrencyValue(),
        availableCurrencies = emptyList(),
        onRequestBack = {

        }, {

        }, "", {

        })
}

@Composable
private fun SelectCurrencyView(
    selectedCurrency: CurrencyValue,
    availableCurrencies: List<CurrencyValue>,
    onRequestBack: () -> Unit,
    onCurrencySelected: (CurrencyValue) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(), elevation = 10.dp, shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(secondaryColor)
                .padding(top = 20.dp, start = 8.dp, end = 8.dp)

        ) {
            HeaderView(onRequestBack)

            Spacer(modifier = Modifier.height(16.dp))


            SearchBox(searchText = searchQuery, onSearch = onSearchChange)

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                columns = GridCells.Fixed(3),
                content = {
                    items(availableCurrencies.filter { currency ->
                        currency.name != selectedCurrency.name && if (searchQuery.isEmpty()) true else currency.name.contains(
                            searchQuery,
                            ignoreCase = true
                        )
                    }) { currency ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCurrencySelected(currency)
                                }
                                .padding(5.dp),
                            elevation = 10.dp,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = currency.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                style = fontRegular.copy(
                                    color = textColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W600,
                                    textAlign = TextAlign.Center
                                )
                            )

                        }
                    }
                },
                horizontalArrangement = Arrangement.Center,
            )


        }
    }
}

@Composable
private fun HeaderView(onRequestBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.clickable {
                onRequestBack()
            },
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                modifier = Modifier.size(height = 12.dp, width = 12.dp),
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Converter",
                style = fontRegular.copy(color = primaryColor, fontSize = 14.sp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Change Currency",
            style = fontBold.copy(color = textColor, fontSize = 14.sp)
        )
    }
}