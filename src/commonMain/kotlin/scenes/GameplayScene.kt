import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.roundRect
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors

class GameplayScene() : Scene() {
    override suspend fun Container.sceneInit() {
        val cellSize = views.virtualWidth / 15.0 // for 8 cells horizontally
        val fieldSize = (cellSize * 15.0 + views.virtualWidth % 15) - 100
        val leftIndent = 50.0
        val topIndent = 150.0

        roundRect(fieldSize, fieldSize+ (14+ cellSize), 5.0, 1.0, Colors.WHITE).run {
            position(leftIndent, topIndent)
            name = "Board"
        }

        for (i in 0..7) {
            for (j in 0..8) {
                roundRect(cellSize, cellSize, 5.0, 1.0, Colors.TRANSPARENT_WHITE, Colors.PINK, 2.0).run {
                    name = "Cell ${i}${j}"
                    x = 14 + (14 + cellSize) * i + leftIndent
                    y = 14 + (14 + cellSize) * j + topIndent

                    onClick {
                        color = Colors.BLACK
                    }
                }
            }
        }
    }
}
