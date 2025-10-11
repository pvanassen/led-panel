package nl.pvanassen.led.animation.fire

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import java.awt.Color
import java.io.IOException
import java.io.UncheckedIOException
import java.util.*
import kotlin.math.abs
import kotlin.math.min

class Fire(private val canvas: Canvas) : Animation<Any> {

    private val random = Random()

    private val fire: List<MutableList<Int>>

    private val palette: List<Int>

    init {
        try {
            fire = (0 until canvas.getHeight())
                .map {
                    (0 until canvas.getWidth())
                        .map { 0 }
                        .toMutableList()
                }
            palette = (0..255)
                .map { it / 255f }
                .map { Color.HSBtoRGB(it * 0.425f, 1f, min(1f, it * 150f)) }

        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {
        (0 until canvas.getWidth())
            .forEach {
                fire[canvas.getHeight() - 1][it] = abs(255 * random.nextDouble()).toInt()
            }
        val w = canvas.getWidth()
        val h = canvas.getHeight()
        (0 until canvas.getHeight()).forEach { y ->
            (0 until canvas.getWidth()).forEach { x ->
                fire[y][x] =
                    ((fire[(y + 1) % h][(x - 1 + w) % w]
                            + fire[(y + 2) % h][(x) % w]
                            + fire[(y + 1) % h][(x + 1) % w]
                            + fire[(y + 3) % h][(x) % w])
                            * 64) / 257;
            }
        }
        (0 until canvas.getHeight()).forEach { y ->
            (0 until canvas.getWidth()).forEach { x ->
                try {
                    canvas.setRGB(x, y, palette[fire[y][x]])
                } catch (e: Exception) {
                    println("x: $x, y: $y, canvas.height: ${canvas.getHeight()}, canvas.width: ${canvas.getWidth()}, val: ${fire[y][x]}")
                    throw e
                }
            }
        }
        return canvas.getValues()
    }

}
