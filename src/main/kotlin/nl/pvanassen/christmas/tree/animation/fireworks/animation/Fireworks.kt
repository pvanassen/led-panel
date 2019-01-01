package nl.pvanassen.christmas.tree.animation.fireworks.animation

import nl.pvanassen.christmas.tree.animation.common.model.Animation
import nl.pvanassen.christmas.tree.animation.common.model.TreeModel
import nl.pvanassen.christmas.tree.canvas.Canvas
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.UncheckedIOException
import java.util.*
import javax.imageio.ImageIO
import javax.inject.Singleton

@Singleton
class Fireworks(private val canvas: Canvas, private val treeModel:TreeModel): Animation<FireworkState> {

    private val random = Random()

    private val fireworkImages: List<BufferedImage>

    private val startY = treeModel.ledsPerStrip - 1

    init {
        try {
            fireworkImages = arrayOf("/firework-blue-1.png",
                    "/firework-gold-1.png",
                    "/firework-gold-2.png",
                    "/firework-gold-3.png",
                    "/firework-gold-4.png",
                    "/firework-green-1.png",
                    "/firework-multi-1.png",
                    "/firework-multi-2.png",
                    "/firework-multi-3.png",
                    "/firework-multi-4.png",
                    "/firework-multi-5.png",
                    "/firework-multi-6.png",
                    "/firework-multi-7.png",
                    "/firework-multi-8.png",
                    "/firework-purple-1.png",
                    "/firework-purple-2.png",
                    "/firework-red-1.png" )
                    .map {ImageIO.read(javaClass.getResourceAsStream(it)) }
                    .toList()
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    override fun getFrame(seed:Long, frame:Int, nsPerFrame:Int, state:FireworkState): ByteArray {
        (0 until treeModel.ledsPerStrip).forEach {pixel ->
            (0 until treeModel.strips).forEach { strip ->
                canvas.setValue(strip, pixel, Color.BLACK.rgb)
            }
        }
        if (frame < state.waitFrames) {
            return canvas.getValues()
        }
        if (state.tail) {
            if (state.y <= 0) {
                state.tail = false
            }
            else {
                (0 until treeModel.ledsPerStrip).forEach { canvas.setValue(7 + state.x, it, 0) }
                canvas.setValue(7 + state.x, state.y--, Color(169, 105, 67).rgb)
            }
        }
        else {
            state.scale += 0.02f

            val img = state.fireworkImage.scale(state.scale, state.scale)
            canvas.setImage(-((canvas.canvas.width / 2) - (img.width / 2)), -(60 - (img.height / 2)), img)
        }
        return canvas.getValues()
    }

    override fun getFixedTimeAnimationFrames(state:FireworkState):Int {
        return state.frames
    }

    override fun isFixedTimeAnimation() = true


    override fun getStateObject(): FireworkState {
        val waitFrames = random.nextInt(50) + 20
        return FireworkState(0.01f, true, startY, fireworkImages[random.nextInt(fireworkImages.size)], random.nextInt(3), waitFrames, (startY + 75) + waitFrames)
    }
}