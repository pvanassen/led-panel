package nl.pvanassen.christmas.tree.animation.morningglory.animation

import nl.pvanassen.christmas.tree.animation.common.model.Animation
import nl.pvanassen.christmas.tree.canvas.Canvas
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.UncheckedIOException
import javax.imageio.ImageIO
import javax.inject.Singleton

@Singleton
class MorningGlory(private val canvas: Canvas): Animation {

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

    override fun getFrame(seed:Long, frame:Int, nsPerFrame:Int): ByteArray {

        canvas.setImage(x, 0, poolImage)

        if (left) {
            x--
        } else {
            x++
        }
        if (x > (poolImage.width - canvas.canvas.width) || x < 0) {
            left = !left
        }

        return canvas.getValues()
    }
}
