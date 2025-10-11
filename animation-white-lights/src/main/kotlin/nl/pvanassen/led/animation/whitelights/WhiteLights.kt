package nl.pvanassen.led.animation.whitelights

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import nl.pvanassen.led.animation.common.util.ColorUtils
import java.util.*

class WhiteLights(private val canvas: Canvas, private val pixels: List<Int>): Animation<Any> {

    private val random = Random()

    private val positions:MutableList<List<Boolean>>

    private var secondsSinceReset = 0.0

    private val color = ColorUtils.makeColor(255, 255, 240)

    private var switch:Boolean = false

    private var lastSwitch = 0

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

        if (secondsSinceReset.toInt().rem(1) == 0 && secondsSinceReset.toInt() != lastSwitch) {
            lastSwitch = secondsSinceReset.toInt()
            switch = !switch
        }

        (pixels.indices).forEach { strip ->
            canvas.setValue(strip, 0, 0)
            canvas.setValue(strip, pixels[strip] - 1, 0)
            (0 until pixels[strip] - 1).map { pixel ->
                val switchedPixel = if (switch) {
                    pixel + 1
                }
                else {
                    pixel
                }
                val pixelColor = if (positions[strip][pixel]) {
                    color
                }
                else {
                    0
                }
                canvas.setValue(strip, switchedPixel, pixelColor)
            }
        }

        return canvas.getValues()
    }
}