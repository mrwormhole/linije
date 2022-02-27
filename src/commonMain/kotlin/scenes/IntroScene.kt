import com.soywiz.klock.seconds
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing

class IntroScene() : Scene() {
    override suspend fun Container.sceneMain() {
        text("MyScene1: ${LinijeModule.WIDTH}")
        solidRect(100, 100, Colors.RED).position(100, 100).run {
            name = "pepehands"
            println(name)
        }

        val minDegrees = (-16).degrees
        val maxDegrees = (+16).degrees


        val image1 = image(AssetLoader.myBitmap) {
            rotation = maxDegrees
            anchor(.5, .5)
            scale(.8)
            position(256, 256)
        }

        while (true) {
            image1.tween(image1::rotation[minDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
            image1.tween(image1::rotation[maxDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
        }
    }
}
