package nl.pvanassen.led.animation.rainbowalgo

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import nl.pvanassen.led.animation.common.util.ColorUtils.makeColorHSB

class RainbowAlgo(private val canvas: Canvas, private val pixels: List<Int>) : Animation<Any> {

    private var cnt: UByte = 0u

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {

        pixels.indices.forEach { strip ->
            (0 until pixels[strip]).forEach { pixel ->
                canvas.setValue(strip, pixel, makeColorHSB((cnt.toInt() + (pixel * 2)).toFloat() / 255, 1f, 1f))
            }
        }
        cnt++


        return canvas.getValues()
    }
}