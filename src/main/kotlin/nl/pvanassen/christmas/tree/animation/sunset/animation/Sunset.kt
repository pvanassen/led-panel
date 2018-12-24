package nl.pvanassen.christmas.tree.animation.sunset.animation

import nl.pvanassen.christmas.tree.animation.common.model.Animation
import nl.pvanassen.christmas.tree.canvas.Canvas
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.UncheckedIOException
import javax.imageio.ImageIO
import javax.inject.Singleton

@Singleton
class Sunset(private val canvas: Canvas): Animation {

    private val sunset: BufferedImage

    private val waitFrames = 4

    private val frames:Int

    private var y:Int = 0

    init {
        try {
            sunset = ImageIO.read(javaClass.getResourceAsStream("/sunset.png"))
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
        frames = sunset.height * waitFrames

        reset()
    }

    override fun getFrame(seed:Long, frame:Int, nsPerFrame:Int): ByteArray {
        canvas.setImage(0, y, sunset)

        if (frame.rem(waitFrames) == 0) {
            y--
        }

        if (frame == frames - 1) {
            reset()
        }

        return canvas.getValues()
    }

    override fun getFixedTimeAnimationFrames() = frames

    override fun isFixedTimeAnimation() = true

    private fun reset() {
        y = canvas.canvas.height + 200
    }
}