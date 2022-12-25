package nl.pvanassen.led.animation.morningglory.animation

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.Registration

class MorningGloryFactory: AnimationFactory<Any> {

    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig) = MorningGlory(canvas)

    override fun getRegistrationInfo() = Registration("morning-glory")
}