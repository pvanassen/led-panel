package nl.pvanassen.led.animation

import kotlinx.serialization.Serializable

@Serializable
data class Message<T>(val type: String, val payload: T)