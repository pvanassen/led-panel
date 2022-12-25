package nl.pvanassen.led.animation.sunset

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.UncheckedIOException
import javax.imageio.ImageIO

class Sunset(private val canvas: Canvas): Animation<Any> {

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

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {
        canvas.setImage(0, y, sunset)

        if (frame.rem(waitFrames) == 0) {
            y--
        }

        if (frame == frames - 1) {
            reset()
        }

        return canvas.getValues()
    }

    override fun getFixedTimeAnimationFrames(helper: Any) = frames

    override fun isFixedTimeAnimation() = true

    private fun reset() {
        y = canvas.getHeight() + 200
    }
}