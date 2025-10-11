package nl.pvanassen.led.animation.disco

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.Animation
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.Registration

class DiscoFactory: AnimationFactory<Any> {
    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig): Animation<Any> =
        Disco(canvas, pixels, config.property("app.scale").getString().toDouble())


    override fun getRegistrationInfo(config: ApplicationConfig) = Registration(config.property("app.name").getString())

}