import com.soywiz.klock.seconds
import com.soywiz.korge.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.geom.shape.Shape2d
import com.soywiz.korma.interpolation.Easing

suspend fun main() = Korge(width = 480, height = 640, title="linije", bgcolor = Colors["#641398"]) {
	//val minDegrees = (-16).degrees
	//val maxDegrees = (+16).degrees

	/*val image = image(resourcesVfs["korge.png"].readBitmap()) {
		rotation = maxDegrees
		anchor(.5, .5)
		scale(.8)
		position(256, 256)
	}*/

	val image2 = image(resourcesVfs["korge.png"].readBitmap()).scale(0.3)
	image2.rotation = 23.0.degrees

	/*val image = image(resourcesVfs["korge.png"].readBitmap()) {
		rotation = maxDegrees
		anchor(.5, .5)
		scale(.8)
		position(256, 256)
	}

	while (true) {
		image.tween(image::rotation[minDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
		image.tween(image::rotation[maxDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
	}*/

	circle(20.0, Colors.YELLOW, Colors.GREEN, 10.0).xy(100, 100)
}