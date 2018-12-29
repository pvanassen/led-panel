package nl.pvanassen.christmas.tree.animation.fireworks.animation

import nl.pvanassen.christmas.tree.animation.common.model.Animation
import nl.pvanassen.christmas.tree.canvas.Canvas
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.UncheckedIOException
import javax.imageio.ImageIO
import javax.inject.Singleton

@Singleton
class Fireworks(private val canvas: Canvas): Animation {

    private val fireworkImage: BufferedImage
    private val tailImage: BufferedImage

    private val frames:Int = 1

    private var scale:Float = 0.01f

    private var tail:Boolean = true

    init {
        try {
            fireworkImage = ImageIO.read(javaClass.getResourceAsStream("/firework-gold.png"))
            tailImage = ImageIO.read(javaClass.getResourceAsStream("/firework-tail-2.png")).scale(.1f, .1f)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    override fun getFrame(seed:Long, frame:Int, nsPerFrame:Int): ByteArray {
        if (tail) {
            canvas.setImage(-80, -50, tailImage, true)
        }
        else {
            if (scale > 1f) {
                scale = 0.01f
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