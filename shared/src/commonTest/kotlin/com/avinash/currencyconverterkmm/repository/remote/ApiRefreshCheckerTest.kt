package com.avinash.currencyconverterkmm.repository.remote

import io.ktor.util.date.*
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ApiRefreshCheckerTest {

    private var apiRefreshChecker = ApiRefreshChecker()


    @Test
    fun returnsFalseWhenTheTimeNotExpires() {

        val currentTime = getTimeMillis()

        assertFalse(apiRefreshChecker.checkIfTimePassed(currentTime))

        assertFalse(apiRefreshChecker.checkIfTimePassed(currentTime + ApiRefreshChecker.API_CACHE_MILLISECONDS))

    }


    @Test
    fun returnsTrueWhenTheTimeExpires() {

        val currentTime = getTimeMillis()

        assertTrue(apiRefreshChecker.checkIfTimePassed(-1))

        assertTrue(apiRefreshChecker.checkIfTimePassed(currentTime - ApiRefreshChecker.API_CACHE_MILLISECONDS))

    }
}