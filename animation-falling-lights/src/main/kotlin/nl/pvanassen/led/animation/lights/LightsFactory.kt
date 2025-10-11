package nl.pvanassen.led.animation.lights

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.Registration

class LightsFactory : AnimationFactory<Any> {
    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig) = Lights(canvas, pixels)

    override fun getRegistrationInfo(config: ApplicationConfig) = Registration("falling-lights")
}