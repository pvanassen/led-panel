package nl.pvanassen.led.animation.whitelights

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.Registration

class WhiteLightsFactory: AnimationFactory<Any> {

    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig) = WhiteLights(canvas, pixels)

    override fun getRegistrationInfo(config: ApplicationConfig) = Registration("white-lights")
}