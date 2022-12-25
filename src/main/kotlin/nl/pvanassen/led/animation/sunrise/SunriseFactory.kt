package nl.pvanassen.led.animation.sunrise

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.AnimationType
import nl.pvanassen.led.animation.common.model.Registration

class SunriseFactory: AnimationFactory<Any> {

    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig) = Sunrise(canvas)

    override fun getRegistrationInfo() = Registration(name = "sunrise", type = AnimationType.ON_STARTUP)
}