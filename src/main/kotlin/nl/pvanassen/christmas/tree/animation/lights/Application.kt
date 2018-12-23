package nl.pvanassen.christmas.tree.animation.lights

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("nl.pvanassen.christmas.tree.animation.lights.animation",
                        "nl.pvanassen.christmas.tree.animation.lights.configuration",
                        "nl.pvanassen.christmas.tree.animation.lights.model",
                        "nl.pvanassen.christmas.tree.animation.lights.controller")
                .mainClass(Application.javaClass)
                .start()
    }
}