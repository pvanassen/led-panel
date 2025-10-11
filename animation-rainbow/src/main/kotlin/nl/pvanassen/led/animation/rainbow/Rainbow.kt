package nl.pvanassen.led.animation.rainbow

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Rainbow(private val canvas: Canvas) : Animation<Any> {

    private val rainbow: BufferedImage

    private var pos = -canvas.getHeight()

    init {
        val img = ImageIO.read(javaClass.getResourceAsStream("/rainbow.png"))
        rainbow = if (img.width < canvas.getWidth()) {
            val factor = canvas.getWidth() / img.width.toDouble()
            val scaled =
                img.getScaledInstance((img.width * factor).toInt(), img.height, Image.SCALE_SMOOTH)
            val buffer = BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_RGB)
            buffer.graphics.drawImage(scaled, 0, 0, null)
            buffer
        } else {
            img
        }
    }

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {
        canvas.setImage(0, pos++, rainbow)
        if (pos > rainbow.height - canvas.getHeight()) {
            pos = -canvas.getHeight()
        }
        return canvas.getValues()
    }

}
