package nl.pvanassen.led.animation.fireworks.animation

import java.awt.image.BufferedImage

data class FireworkState(var scale:Float, var tail:Boolean, var y:Int, val fireworkImage: BufferedImage, val x:Int, val waitFrames:Int, val frames:Int)