package nl.pvanassen.christmas.tree.animation.white.lights.animation

import nl.pvanassen.christmas.tree.animation.common.model.Animation
import nl.pvanassen.christmas.tree.animation.common.model.TreeModel
import nl.pvanassen.christmas.tree.animation.common.util.ColorUtils
import nl.pvanassen.christmas.tree.canvas.Canvas
import java.util.*
import javax.inject.Singleton

@Singleton
class Lights(private val canvas: Canvas, private val treeModel: TreeModel): Animation {

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

    private fun createPositions() = (0 until treeModel.strips).map {
        val litPixels = arrayOf(random.nextInt(treeModel.ledsPerStrip), random.nextInt(treeModel.ledsPerStrip), random.nextInt(treeModel.ledsPerStrip))
        (0 until treeModel.ledsPerStrip - 1).map { pixel ->
            litPixels.contains(pixel)
        }
    }



    override fun getFrame(seed:Long, frame:Int, nsPerFrame:Int): ByteArray {
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

        (0 until treeModel.strips).forEach {strip ->
            canvas.setValue(strip, 0, 0)
            canvas.setValue(strip, treeModel.ledsPerStrip - 1, 0)
            (0 until treeModel.ledsPerStrip - 1).forEach {pixel ->
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