package nl.pvanassen.led.animation.sunset

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.AnimationType
import nl.pvanassen.led.animation.common.model.Registration

class SunsetFactory: AnimationFactory<Any> {

    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig) = Sunset(canvas)

    override fun getRegistrationInfo(config: ApplicationConfig) = Registration(name = "sunset", type = AnimationType.ON_SHUTDOWN)
}