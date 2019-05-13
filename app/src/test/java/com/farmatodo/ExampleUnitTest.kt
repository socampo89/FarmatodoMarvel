package com.farmatodo

import com.farmatodo.utils.MathUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun multiple_isCorrect() {
        assertEquals(true, MathUtils.isMultiple(3, 3))
        assertEquals(false, MathUtils.isMultiple(10, 7))
    }
}
