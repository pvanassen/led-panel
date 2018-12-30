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
class Fireworks(private val canvas: Canvas, private val treeModel:TreeModel): Animation {

    private val random = Random()

    private val fireworkImages: List<BufferedImage>

    private var fireworkImage: BufferedImage


    private var scale:Float = 0.01f

    private var tail:Boolean = true

    private val startY = treeModel.ledsPerStrip - 1

    private var y = startY

    private var x = 0

    private val frames:Int = (startY + 50)

    init {
        try {
            fireworkImages = arrayOf("/firework-gold.png", "/firework-multi.png", "/firework-red.png", "/firework-red-blue.png")
                    .map {ImageIO.read(javaClass.getResourceAsStream(it)) }
                    .toList()
            fireworkImage = fireworkImages[0]
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    override fun getFrame(seed:Long, frame:Int, nsPerFrame:Int): ByteArray {
        if (tail) {
            if (y <= 0) {
                (0 until treeModel.ledsPerStrip).forEach {pixel ->
                    (0 until treeModel.strips).forEach { strip ->
                        canvas.setValue(strip, pixel, Color.WHITE.rgb)
                    }
                }
                tail = false
            }
            else {
                (0 until treeModel.ledsPerStrip).forEach { canvas.setValue(7 + x, it, 0) }
                canvas.setValue(7 + x, y--, Color(169, 105, 67).rgb)
            }
        }
        else {
            if (scale > 1f) {
                scale = 0.01f
                tail = true
                y = startY
                fireworkImage = fireworkImages[random.nextInt(fireworkImages.size)]
                x = random.nextInt(3)
            }
            scale += 0.02f

            val img = fireworkImage.scale(scale, scale)
            canvas.setImage(-((canvas.canvas.width / 2) - (img.width / 2)), -(60 - (img.height / 2)), img)
        }
        return canvas.getValues()
    }

    override fun getFixedTimeAnimationFrames() = frames

    override fun isFixedTimeAnimation() = true
}