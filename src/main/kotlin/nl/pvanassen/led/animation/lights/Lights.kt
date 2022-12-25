package nl.pvanassen.led.animation.lights

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import nl.pvanassen.led.animation.common.util.ColorUtils
import java.util.*
import kotlin.collections.ArrayList


class Lights(private val canvas: Canvas, private val pixels: List<Int>) : Animation<Any> {

    private val random = Random()

    private var cnt: Double = 0.toDouble()

    private val positions: MutableList<List<Boolean>>

    private var secondsSinceReset = 0.0

    init {
        positions = ArrayList()
        positions.addAll(createPositions())
    }

    private fun createPositions(): List<List<Boolean>> {
        return (pixels.indices).map {
            val litPixels = arrayOf(random.nextInt(pixels[it]), random.nextInt(pixels[it]), random.nextInt(pixels[it]))
            (0 until pixels[it]).map { pixel ->
                litPixels.contains(pixel)
            }
        }
    }

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {
        val color = this.color
        secondsSinceReset += nsPerFrame / 1000_000_000.0

        if (secondsSinceReset > 120) {
            secondsSinceReset = 0.0
            positions.clear()
            positions.addAll(createPositions())
        }

        (pixels.indices).forEach { strip ->
            (0 until pixels[strip]).map { pixel ->
                val pixelColor = if (positions[strip][pixel]) {
                    color
                } else {
                    0
                }
                canvas.setValue(strip, pixel, pixelColor)
            }
        }

        return canvas.getValues()
    }

    private val color: Int
        get() {
            cnt += 0.01.coerceAtMost(Math.random() / 1000.0)
            if (cnt > 1) {
                cnt--
            }
            return ColorUtils.makeColorHSB(cnt.toFloat(), 1f, 1f)
        }

}