package nl.pvanassen.christmas.tree.animation.sunset

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("nl.pvanassen.christmas.tree.animation.sunset.animation",
                        "nl.pvanassen.christmas.tree.animation.sunset.configuration",
                        "nl.pvanassen.christmas.tree.animation.sunset.model",
                        "nl.pvanassen.christmas.tree.animation.sunset.controller")
                .mainClass(Application.javaClass)
                .start()
    }
}