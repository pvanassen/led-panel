package nl.pvanassen.christmas.tree.animation.sunrise

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("nl.pvanassen.christmas.tree.animation.sunrise.animation",
                        "nl.pvanassen.christmas.tree.animation.sunrise.configuration",
                        "nl.pvanassen.christmas.tree.animation.sunrise.model",
                        "nl.pvanassen.christmas.tree.animation.sunrise.controller")
                .mainClass(Application.javaClass)
                .start()
    }
}