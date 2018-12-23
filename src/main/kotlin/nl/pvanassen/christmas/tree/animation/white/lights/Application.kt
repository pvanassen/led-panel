package nl.pvanassen.christmas.tree.animation.white.lights

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("nl.pvanassen.christmas.tree.animation.white.lights.animation",
                        "nl.pvanassen.christmas.tree.animation.white.lights.configuration",
                        "nl.pvanassen.christmas.tree.animation.white.lights.model",
                        "nl.pvanassen.christmas.tree.animation.white.lights.controller")
                .mainClass(Application.javaClass)
                .start()
    }
}