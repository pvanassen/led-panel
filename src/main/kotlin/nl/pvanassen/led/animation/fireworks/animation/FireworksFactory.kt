package nl.pvanassen.led.animation.fireworks.animation

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.AnimationType
import nl.pvanassen.led.animation.common.model.Registration

class FireworksFactory: AnimationFactory<FireworkState> {
    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig): Animation<FireworkState> = Fireworks(canvas, pixels)

    override fun getRegistrationInfo() = Registration("fireworks", AnimationType.TIMED, "0 59 23 31 12 * *")
}