package com.avinash.currencyconverterkmm.di

import com.avinash.currencyconverterkmm.repository.Repository
import com.avinash.currencyconverterkmm.repository.local.LocalDataSource
import com.avinash.currencyconverterkmm.repository.remote.ApiRefreshChecker
import com.avinash.currencyconverterkmm.repository.remote.ApiService
import com.avinash.currencyconverterkmm.repository.remote.RemoteDataSource
import com.avinash.currencyconverterkmm.viewmodel.CurrencyViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule)
}

val commonModule = module {
    singleOf(::ApiService)
    singleOf(::LocalDataSource)
    singleOf(::RemoteDataSource)
    singleOf(::ApiRefreshChecker)
    singleOf(::Repository)
    singleOf(::CurrencyViewModel)
}