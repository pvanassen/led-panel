package nl.pvanassen.christmas.tree.animation.morningglory

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("nl.pvanassen.christmas.tree.animation.morningglory.animation")
                .mainClass(Application.javaClass)
                .start()
    }
}