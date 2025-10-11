package nl.pvanassen.led.animation.christmastree

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import java.awt.Image
import javax.imageio.ImageIO

class ChristmasTree(private val canvas: Canvas) : Animation<Any> {

    private val images: List<Image> = (1..6).map {
        ImageIO.read(javaClass.getResourceAsStream("/tree$it.png"))
            .getScaledInstance(canvas.getWidth(), canvas.getHeight(), Image.SCALE_FAST)!!
    }

    private val base = ImageIO.read(javaClass.getResourceAsStream("/base.png"))
        .getScaledInstance(canvas.getWidth(), canvas.getHeight(), Image.SCALE_FAST)!!

    private var image = images[0]

    private var secondsSinceChange = 0.0

    override fun getFrame(seed: Long, frame: Int, nsPerFrame: Int, helper: Any): ByteArray {
        secondsSinceChange += nsPerFrame / 1000_000_000.0

        if (secondsSinceChange > 3) {
            image = images.random()
            secondsSinceChange = 0.0
        }
        canvas.drawImage(base)
        canvas.drawImage(image)

        return canvas.getValues()
    }
}
