package core

import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.Circle
import com.soywiz.korge.view.RoundRect
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.position
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

class Board(virtualWidth: Int,
            divideWidthBy: Int,
            leftIndent: Double,
            topIndent: Double,
            rows: Int,
            columns: Int,
            color: RGBA
) {
    val drawable: RoundRect
    private val cellSize: Double
    private val boardSize: Double
    private val rx: Double = 5.0
    private val ry: Double = 1.0
    private val leftIndent: Double
    private val topIndent: Double
    private val rowCellCount: Int
    private val columnCellCount: Int

    private var cells: Array<Array<Cell>> = emptyArray()
    private var indicators: Array<Array<Indicator>> = emptyArray()

    init {
        this.cellSize = virtualWidth / divideWidthBy.toDouble()
        this.boardSize =  (cellSize * divideWidthBy.toDouble() + virtualWidth % divideWidthBy) - 100
        this.leftIndent = leftIndent
        this.topIndent = topIndent
        this.rowCellCount = rows
        this.columnCellCount = columns

        drawable = RoundRect(boardSize, boardSize, rx, ry, color)
            .apply {
                position(leftIndent, topIndent)
                name = "Board"
            }

        cells = Array(rowCellCount) {
            Array(columnCellCount) {
                Cell()
            }
        }

        indicators = Array(rowCellCount + 1) {
            Array(columnCellCount + 1) {
                Indicator()
            }
        }
    }

    fun makeCells(color: RGBA = Colors.PINK, borderColor: RGBA = Colors.TRANSPARENT_WHITE, borderThickness: Double = 2.0) {
        val cellIndent =  (boardSize - ((cellSize) * columnCellCount)) /
                (columnCellCount + 1)

        println("cell Indent is $cellIndent, cell Size is $cellSize, board Size is $boardSize")

        for (i in 0 until rowCellCount) {
            for(j in 0 until columnCellCount) {
                val leftCellIndent = leftIndent + cellIndent * (j + 1)
                val topCellIndent = topIndent + cellIndent * (i + 1)

                cells[i][j] = Cell(cellSize, color, borderColor, borderThickness).apply {
                    drawable.name = "Cell ${i}${j}"
                    drawable.x = cellSize * j + leftCellIndent
                    drawable.y = cellSize * i + topCellIndent

                    drawable.onClick {
                        drawable.color = Colors.GREEN
                        println("Clicked on $drawable.name")
                    }
                }
            }
        }
    }

    fun makeIndicators(color: RGBA = Colors.BLUE, borderColor: RGBA = Colors.TRANSPARENT_WHITE, borderThickness: Double = 2.0) {
        val radius = cellSize / 2

        val cellIndent =  (boardSize - ((cellSize) * columnCellCount)) /
                (columnCellCount + 1)

        for (i in 0..rowCellCount) {
            for (j in 0..columnCellCount) {
                val leftCellIndent = leftIndent + cellIndent * (j + 1)
                val topCellIndent = topIndent + cellIndent * (i + 1)

                indicators[i][j] = Indicator(radius, color, borderColor, borderThickness).apply {
                    drawable.name = "Indicator ${i}${j}"
                    drawable.x = radius * 2 * j + leftCellIndent
                    drawable.y = radius * 2 * i + topCellIndent
                }
            }
        }
    }

    fun getDrawableCells(): Pair<Array<Array<RoundRect>>, Array<Array<Text>>> {
        val drawableCells: Array<Array<RoundRect>> = Array(rowCellCount) {
            Array(columnCellCount) {
                RoundRect(0.0,0.0,0.0,0.0)
            }
        }
        val drawableCellsText: Array<Array<Text>> = Array(rowCellCount) {
            Array(columnCellCount) {
                Text("Hello")
            }
        }

        for (i in 0 until rowCellCount) {
            for (j in 0 until columnCellCount) {
                drawableCells[i][j] = cells[i][j].drawable
            }
        }
        for (i in 0 until rowCellCount) {
            for (j in 0 until columnCellCount) {
                drawableCellsText[i][j] = cells[i][j].text
            }
        }

        return Pair(drawableCells, drawableCellsText)
    }

    fun getDrawableIndicators(): Array<Array<Circle>> {
        val drawableIndicators: Array<Array<Circle>> = Array(rowCellCount+1) {
            Array(columnCellCount+1) {
                Circle()
            }
        }

        for (i in 0 .. rowCellCount) {
            for (j in 0 .. columnCellCount) {
                drawableIndicators[i][j] = indicators[i][j].drawable
            }
        }

        return drawableIndicators
    }
}