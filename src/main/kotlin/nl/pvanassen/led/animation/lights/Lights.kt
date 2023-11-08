package nl.pvanassen.led.animation.lights

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import java.awt.Color
import java.util.*


class Lights(private val canvas: Canvas, private val pixels: List<Int>) : Animation<Any> {

    private val random = Random()

    private val positions = HashMap<Int, Int>()

    private val white = Color.WHITE.rgb

    private val darkerWhite = Color.WHITE.darker().rgb

    private val darkestWhite = Color.WHITE.darker().darker().rgb

    private val almostBlack = Color.WHITE.darker().darker().darker().rgb

    init {
        positions[random.nextInt(pixels.size)] = 0
    }

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {
        if (frame % 2 == 0) {
            pixels.forEachIndexed { strip, pixels ->
                (0 until pixels).forEach { pixel ->
                    canvas.setValue(strip, pixel, Color.BLACK.rgb)
                }
            }
            positions.forEach { (strip, pixel) ->
                if (pixel < pixels[strip]) {
                    canvas.setValue(strip, pixel, white)
                }
                if (pixel > 0 && pixel < pixels[strip] + 1) {
                    canvas.setValue(strip, pixel - 1, darkerWhite)
                }
                if (pixel > 1 && pixel < pixels[strip] + 2) {
                    canvas.setValue(strip, pixel - 2, darkestWhite)
                }
                if (pixel > 2 && pixel < pixels[strip] + 3) {
                    canvas.setValue(strip, pixel - 3, almostBlack)
                }
            }

            val newPosition = positions
                .filter { (strip, _) ->
                    positions[strip] != pixels[strip] + 2
                }
                .map { (strip, pixel) -> Pair(strip, pixel + 1) }
                .toMap()

            positions.clear()
            positions.putAll(newPosition)

            if (newPosition.isEmpty()) {
                positions[random.nextInt(pixels.size)] = 0
            }
        }
        return canvas.getValues()
    }
}