import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korim.color.Colors
import core.Board

class GameplayScene() : Scene() {
    override suspend fun Container.sceneInit() {
        val leftIndent = 50.0
        val topIndent = 150.0

        val board = Board(views.virtualWidth, 15, leftIndent, topIndent, 8, 9, Colors.WHITE)
        addChild(board.getDrawableBoard())
        board.makeCells()
        for (rowCells in board.getDrawableCells()) {
            addChildren(rowCells.toList())
        }
    }
}
