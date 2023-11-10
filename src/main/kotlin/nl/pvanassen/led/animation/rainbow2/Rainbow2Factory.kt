package nl.pvanassen.led.animation.rainbow2

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.Registration

class Rainbow2Factory : AnimationFactory<Any> {
    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig): Animation<Any> =
        Rainbow2(canvas, pixels)


    override fun getRegistrationInfo(config: ApplicationConfig) = Registration(config.property("app.name").getString())

}