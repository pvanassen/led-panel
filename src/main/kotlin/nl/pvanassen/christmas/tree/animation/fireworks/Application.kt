package nl.pvanassen.christmas.tree.animation.fireworks

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("nl.pvanassen.christmas.tree.animation.fireworks.animation",
                        "nl.pvanassen.christmas.tree.animation.fireworks.configuration",
                        "nl.pvanassen.christmas.tree.animation.fireworks.model",
                        "nl.pvanassen.christmas.tree.animation.fireworks.controller")
                .mainClass(Application.javaClass)
                .start()
    }
}