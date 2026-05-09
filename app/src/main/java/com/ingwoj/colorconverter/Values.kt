package com.ingwoj.colorconverter

import kotlin.math.roundToInt

data class Values(
    var r: Int,
    var g: Int,
    var b: Int,

    var c: Float,
    var m: Float,
    var y: Float,
    var k: Float
) {

    /**
     * Converts RGB values to CMYK
     */
    fun calcCMYK() {
        // Normalizing rgb values to be in range
        val rN = r.coerceIn(0, 255) / 255f
        val gN = g.coerceIn(0, 255) / 255f
        val bN = b.coerceIn(0, 255) / 255f

        val kN = 1f - maxOf(rN, gN, bN)
        val z = 1f - kN

        if (z > 0f) {
            c = ((1f - rN - kN) / z * 100f).coerceIn(0f, 100f)
            m = ((1f - gN - kN) / z * 100f).coerceIn(0f, 100f)
            y = ((1f - bN - kN) / z * 100f).coerceIn(0f, 100f)
            k = (kN * 100f).coerceIn(0f, 100f)
        } else {
            // black colour RGB = 0
            c = 0f
            m = 0f
            y = 0f
            k = 100f
        }
    }

    /**
     * Converts CMYK values to RGB
     */
    fun calcRGB() {
        val cF = (c.coerceIn(0f, 100f) / 100f)
        val mF = (m.coerceIn(0f, 100f) / 100f)
        val yF = (y.coerceIn(0f, 100f) / 100f)
        val kF = (k.coerceIn(0f, 100f) / 100f)

        val x = 1f - kF
        r = (255f * (1f - cF) * x).roundToInt().coerceIn(0, 255)
        g = (255f * (1f - mF) * x).roundToInt().coerceIn(0, 255)
        b = (255f * (1f - yF) * x).roundToInt().coerceIn(0, 255)
    }
}