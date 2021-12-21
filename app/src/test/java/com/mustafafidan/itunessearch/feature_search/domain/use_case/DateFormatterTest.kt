package com.mustafafidan.itunessearch.feature_search.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DateFormatterTest{
    @Test
    fun `date output should be equal given date string`() {
        val dateFormatter = DateFormatter()
        val formattedDate = dateFormatter.provideDate("2016-08-17T07:00:00Z")
        assertThat("17 August 2016 07:00:00").isEqualTo(formattedDate)
    }
}