package nl.pvanassen.led.animation.christmastree

import io.ktor.server.config.*
import nl.pvanassen.led.animation.common.canvas.Canvas
import nl.pvanassen.led.animation.common.model.AnimationFactory
import nl.pvanassen.led.animation.common.model.Registration

class ChristmasTreeFactory : AnimationFactory<Any> {

    override fun getAnimation(canvas: Canvas, pixels: List<Int>, config: ApplicationConfig) = ChristmasTree(canvas)

    override fun getRegistrationInfo(config: ApplicationConfig) = Registration("christmas-tree")
}