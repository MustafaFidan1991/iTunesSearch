package com.mustafafidan.itunessearch.feature_search.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CurrencyConverterTest{
    @Test
    fun `currency icon should return $ when currency string USD`() {
        val currencyConverter = CurrencyConverter()
        val currencyIcon = currencyConverter.getCurrencyIcon("USD")
        assertThat(currencyIcon).isEqualTo("$")
    }

    @Test
    fun `currency icon should empty string when currency string empty`() {
        val currencyConverter = CurrencyConverter()
        val currencyIcon = currencyConverter.getCurrencyIcon("")
        assertThat(currencyIcon).isEqualTo("")
    }
}