package nl.pvanassen.christmas.tree.animation.lights.animation

import nl.pvanassen.christmas.tree.animation.common.model.Animation
import nl.pvanassen.christmas.tree.animation.common.model.TreeModel
import nl.pvanassen.christmas.tree.animation.common.util.ColorUtils
import nl.pvanassen.christmas.tree.canvas.Canvas
import java.util.*
import javax.inject.Singleton

@Singleton
class Lights(private val canvas: Canvas, private val treeModel: TreeModel): Animation {

    private val random = Random()

    private var cnt: Double = 0.toDouble()

    private val positions:MutableList<List<Boolean>>

    private var secondsSinceReset = 0.0

    init {
        positions = ArrayList()
        positions.addAll(createPositions())
    }

    private fun createPositions() = (0 until treeModel.strips).map {
        val litPixels = arrayOf(random.nextInt(treeModel.ledsPerStrip), random.nextInt(treeModel.ledsPerStrip), random.nextInt(treeModel.ledsPerStrip))
        (0 until treeModel.ledsPerStrip).map { pixel ->
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

        (0 until treeModel.strips).forEach {strip ->
            (0 until treeModel.ledsPerStrip).forEach {pixel ->
                val pixelColor = if (positions[strip][pixel]) {
                    color
                }
                else {
                    0
                }
                canvas.setValue(strip, pixel, pixelColor)
            }
        }

        return canvas.getValues()
    }

    private val color: Int
        get() {
            cnt += Math.min(0.01, Math.random() / 1000.0)
            if (cnt > 1) {
                cnt--
            }
            return ColorUtils.makeColorHSB(cnt.toFloat(), 1f, 1f)
        }

}