package com.avinash.currencyconverterkmm.repository.remote



class ApiRefreshChecker {

    companion object {
        const val API_CACHE_MILLISECONDS = 30 * 60 * 1000
    }

    fun checkIfTimePassed(rateLmt: Long): Boolean {
        return true//System.currentTimeMillis() >= rateLmt + API_CACHE_MILLISECONDS
    }
}