package nl.pvanassen.christmas.tree.animation.fireworks.animation

import java.awt.image.BufferedImage

class FireworkState(var scale:Float, var tail:Boolean, var y:Int, val fireworkImage: BufferedImage, val x:Int, val waitFrames:Int, val frames:Int) {

    /*
            scale = 0.01f
        tail = true
        y = startY
        fireworkImage = fireworkImages[random.nextInt(fireworkImages.size)]
        x = random.nextInt(3)
        waitFrames = random.nextInt(50) + 50

     */
}