package nl.pvanassen.led.animation.fireworks.animation

import java.awt.Image
import java.awt.image.BufferedImage

private operator fun Int.get(n: Int) = (this shr (n * 8)) and 0xFF

private fun lerp(s: Float, e: Float, t: Float) = s + (e - s) * t

private fun blerp(c00: Float, c10: Float, c01: Float, c11: Float, tx: Float, ty: Float) =
        lerp(lerp(c00, c10, tx), lerp(c01,c11, tx), ty)

fun BufferedImage.scale(width: Int, height: Int): BufferedImage {
    val scaleX = this.width / width.toFloat()
    val scaleY = this.height / height.toFloat()
    return scale(scaleX, scaleY)
}

fun BufferedImage.scale(scaleX: Float, scaleY: Float): BufferedImage {
    val scaled = this.getScaledInstance((width * scaleX).toInt(),
        (height * scaleY).toInt(), Image.SCALE_FAST)

    val buffer = BufferedImage((width * scaleX).toInt(),
        (height * scaleY).toInt(), BufferedImage.TYPE_INT_RGB)
    buffer.graphics.drawImage(scaled, 0, 0, null)
    return buffer
}
