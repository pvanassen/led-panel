package nl.pvanassen.led.animation.morningglory.animation

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.UncheckedIOException
import javax.imageio.ImageIO

class MorningGlory(private val canvas: Canvas): Animation<Any> {

    private val poolImage:BufferedImage

    private var x:Int

    private var left = false

    init {
        try {
            poolImage = ImageIO.read(javaClass.getResourceAsStream("/pool.png"))
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
        x = (poolImage.width / 2)
    }

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {
        canvas.setImage(x, 0, poolImage)

        if (left) {
            x--
        } else {
            x++
        }
        if (x > (poolImage.width - canvas.getWidth()) || x < 0) {
            left = !left
        }

        return canvas.getValues()
    }
}
