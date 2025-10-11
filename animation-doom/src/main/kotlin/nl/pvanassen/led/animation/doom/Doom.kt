package nl.pvanassen.led.animation.doom

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.max


class Doom(private val canvas: Canvas) : Animation<Any> {

    companion object {

        private const val FIRE_HEIGHT = 200
        private const val FIRE_WIDTH = 200

        private const val SIZE_RECT = 4

        private const val SIZE_RECT_HEIGHT = FIRE_HEIGHT / SIZE_RECT
        private const val SIZE_RECT_WIDTH = FIRE_WIDTH / SIZE_RECT
    }

    private val buffer = BufferedImage(FIRE_WIDTH, FIRE_HEIGHT, BufferedImage.TYPE_INT_RGB)

    private val fireRectsArray: MutableList<Int> = ArrayList()

    private val coordinatesFire: MutableList<Coordinate> = ArrayList()

    private val palette = listOf(
        Color(7, 7, 7), Color(31, 7, 7), Color(47, 15, 7),
        Color(71, 15, 7), Color(87, 23, 7), Color(103, 31, 7), Color(119, 31, 7),
        Color(143, 39, 7), Color(159, 47, 7), Color(175, 63, 7), Color(191, 71, 7),
        Color(199, 71, 7), Color(223, 79, 7), Color(223, 87, 7), Color(223, 87, 7),
        Color(215, 95, 7), Color(215, 95, 7), Color(215, 103, 15), Color(207, 111, 15),
        Color(207, 119, 15), Color(207, 127, 15), Color(207, 135, 23), Color(207, 135, 23),
        Color(199, 135, 23), Color(199, 143, 23), Color(199, 151, 31), Color(191, 159, 31),
        Color(191, 159, 31), Color(191, 167, 39), Color(191, 167, 39), Color(191, 175, 47),
        Color(183, 175, 47), Color(183, 183, 47), Color(183, 183, 55), Color(207, 207, 111),
        Color(123, 123, 159), Color(0, 0, 199), Color(0, 0, 255)
    )

    private val maxFire = palette.size - 1

    private fun createFireDataStructure() {
        val numberOfRects = SIZE_RECT_HEIGHT * SIZE_RECT_WIDTH
        for (i in 0 until numberOfRects) {
            fireRectsArray.add(0)
        }
    }

    private fun createFireSource() {
        for (column in 0 until SIZE_RECT_WIDTH) {
            val overFlowRectIndex = SIZE_RECT_WIDTH * SIZE_RECT_HEIGHT
            val rectIndex = overFlowRectIndex - SIZE_RECT_WIDTH + column
            fireRectsArray[rectIndex] = maxFire
        }
    }

    private fun renderFire() {
        var row = 0
        while (row < FIRE_HEIGHT) {
            var column = 0
            while (column < FIRE_WIDTH) {
                coordinatesFire.add(Coordinate(column, row))
                column += SIZE_RECT
            }
            row += SIZE_RECT
        }
    }

    private fun calculateFirePropagation() {
        for (column in 0 until SIZE_RECT_WIDTH) {
            for (row in 0 until SIZE_RECT_HEIGHT) {
                val rectIndex = column + SIZE_RECT_WIDTH * row
                updateFireIntensityPerRect(rectIndex)
            }
        }
    }

    private fun updateFireIntensityPerRect(currentRectIndex: Int) {
        val random: Random = ThreadLocalRandom.current()
        val belowRectIndex = currentRectIndex + SIZE_RECT_WIDTH
        if (belowRectIndex >= SIZE_RECT_WIDTH * SIZE_RECT_HEIGHT) {
            return
        }
        val decay = random.nextInt(3)
        val belowRectFireIntensity = fireRectsArray[belowRectIndex]
        val newFireIntensity = max(belowRectFireIntensity - decay, 0)
        fireRectsArray[if (currentRectIndex - decay < 0) currentRectIndex else currentRectIndex - decay] =
            newFireIntensity
    }


    init {
        createFireDataStructure()
        createFireSource()
        renderFire()
    }

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {
        val g = buffer.graphics
        for ((rectIndex, coo) in coordinatesFire.withIndex()) {
            g.color = palette[fireRectsArray[rectIndex]]
            g.fillRect(coo.row, coo.column, SIZE_RECT_WIDTH, SIZE_RECT_HEIGHT)
        }

        canvas.drawImage(buffer.getScaledInstance(canvas.getWidth(), canvas.getHeight(), Image.SCALE_FAST))

        calculateFirePropagation()

        return canvas.getValues()
    }


    data class Coordinate(var row: Int, var column: Int)
}