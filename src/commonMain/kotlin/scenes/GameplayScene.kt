import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.centerOn
import com.soywiz.korim.color.Colors
import core.Board

class GameplayScene() : Scene() {
    override suspend fun Container.sceneInit() {
        val leftIndent = 50.0
        val topIndent = 150.0

        val board = Board(views.virtualWidth, 15, leftIndent, topIndent, 8, 9, Colors.WHITE)
        addChild(board.drawable)

        board.makeCells()
        val (cells, lines) = board.getDrawableCells()
        for (i in cells.indices) {
            for (j in cells[i].indices) {
                val cel = cells[i][j]
                val line = lines[i][j]
                addChild(cel)
                addChild(line)
            }
        }

        board.makeIndicators()
        val (indicators, texts) = board.getDrawableIndicators()
        for (i in indicators.indices) {
            for (j in indicators[i].indices) {
                val indicator = indicators[i][j]
                val text = texts[i][j]
                addChild(indicator)
                addChild(text)
                text.centerOn(indicator)
            }
        }
    }
}
