package com.avinash.currencyconverterkmm.repository.remote

import com.avinash.currencyconverterkmm.getCurrentMilliseconds


class ApiRefreshChecker {

    companion object {
        const val API_CACHE_MILLISECONDS = 30 * 60 * 1000
    }

    fun checkIfTimePassed(rateLmt: Long): Boolean {
        return getCurrentMilliseconds() >= rateLmt + API_CACHE_MILLISECONDS
    }
}