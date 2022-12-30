package nl.pvanassen.led.animation.fireworks.animation

import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*
import javax.imageio.ImageIO


class Fireworks(private val canvas: Canvas, private val pixels:List<Int>): Animation<FireworkState> {

    private val random = Random()

    private val fireworkImages: List<BufferedImage> = arrayOf("/firework-blue-1.png",
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
            .map { ImageIO.read(javaClass.getResourceAsStream(it)) }
            .map { it.scale(canvas.getWidth(), canvas.getHeight()) }
            .toList()

    private val startY = pixels.min() - 1

    override fun getFrame(seed:Long, frame:Int, nsPerFrame:Int, helper:FireworkState): ByteArray {
        (pixels.indices).forEach { strip ->
            (0 until pixels[strip]).forEach { pixel ->
                canvas.setValue(strip, pixel, Color.BLACK.rgb)
            }
        }
        if (frame < helper.waitFrames) {
            return canvas.getValues()
        }
        if (helper.tail) {
            if (helper.y <= 0) {
                helper.tail = false
            }
            else {
                canvas.setValue(7 + helper.x, helper.y--, Color(169, 105, 67).rgb)
            }
        }
        else {
            helper.scale += 0.02f

            val img = helper.fireworkImage.scale(helper.scale, helper.scale)
            canvas.setImage(-((canvas.getWidth() / 2) - (img.width / 2)), -(60 - (img.height / 2)), img)
        }
        return canvas.getValues()
    }

    override fun getFixedTimeAnimationFrames(helper:FireworkState):Int {
        return helper.frames
    }

    override fun isFixedTimeAnimation() = true

    override fun getStateObject(): FireworkState {
        val waitFrames = random.nextInt(50) + 20
        return FireworkState(0.01f,
                true,
                startY,
                fireworkImages[random.nextInt(fireworkImages.size)],
                random.nextInt(3),
                waitFrames,
                (startY + 75) + waitFrames)
    }
}