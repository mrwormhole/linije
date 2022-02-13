import com.soywiz.klock.seconds
import com.soywiz.korge.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.geom.shape.Shape2d
import com.soywiz.korma.geom.vector.roundRect
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

	val cellSize = views.virtualWidth / 15.0 // for 8 cells horizontally
	val fieldSize = (cellSize * 15.0 + views.virtualWidth % 15) - 100
	val leftIndent = 50.0
	val topIndent = 150.0

	roundRect(fieldSize, fieldSize+ (14+ cellSize), 5.0, fill = Colors.YELLOW).apply {
		position(leftIndent, topIndent)
	}

	graphics {
		position(leftIndent, topIndent)
		fill(Colors.BLUE) {
			for (i in 0..7) {
				for (j in 0..8) {
					roundRect(14 + (14 + cellSize) * i, 14 + (14 + cellSize) * j, cellSize, cellSize, 5.0)
				}
			}
		}
	}

}