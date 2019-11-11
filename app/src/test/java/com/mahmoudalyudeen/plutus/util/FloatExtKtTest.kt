package com.mahmoudalyudeen.plutus.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FloatExtKtTest {

    @Test
    fun dollarsWithSign() {
        /** GIVEN - a dollar amount */
        val dollars = 1f

        /** WHEN - it's converted into a dollar with $ sign */
        val actual = dollars.dollarsWithSign()

        /** THEN - it matches the signed dollar format */
        val expected = "$1"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun timestampToShortDate() {
        /** GIVEN - a timestamp */
        val timestamp = 1f

        /** WHEN - it's converted into a short date */
        val actual = timestamp.timestampToShortDate()

        /** THEN - it matches the short date format */
        val expected = "Jan. 70"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun timestampToFullDate() {
        /** GIVEN - a timestamp */
        val timestamp = 1f

        /** WHEN - it's converted into a full date */
        val actual = timestamp.timestampToFullDate()

        /** THEN - it matches the full date format */
        val expected = "01/01/1970"
        assertThat(actual).isEqualTo(expected)
    }
}