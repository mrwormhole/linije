package core

import com.soywiz.korge.view.RoundRect
import com.soywiz.korge.view.position
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

class Board(virtualWidth: Int,
            leftIndent: Double,
            topIndent: Double,
            rows: Int,
            columns: Int,
            color: RGBA
) {
    private val drawableBoard: RoundRect
    private val cellSize: Double
    private val boardSize: Double
    private val rx: Double = 5.0
    private val ry: Double = 1.0
    private val leftIndent: Double
    private val topIndent: Double
    private val rowCellCount: Int
    private val columnCellCount: Int

    private var boardColor: RGBA
    private var cells: Array<Array<Cell>> = emptyArray()
    private var cellBorderColor: RGBA
    private var cellBorderThickness: Double = 2.0
    private var cellColor: RGBA
    private var cellIndent: Double

    init {
        this.cellSize = virtualWidth / 15.0 // TODO could this 15 become a variable we can utilize?
        this.boardSize =  (cellSize * 15.0 + virtualWidth % 15) - 100
        this.leftIndent = leftIndent
        this.topIndent = topIndent
        this.boardColor = color
        this.rowCellCount = rows
        this.columnCellCount = columns

        drawableBoard = RoundRect(boardSize, boardSize, rx, ry, Colors.WHITE)
            .apply {
                position(leftIndent, topIndent)
                name = "Board"
            }

        cells = Array(rowCellCount) {
            Array(columnCellCount) {
                Cell(i=0, j=0,
                    leftIndent = 0.0, topIndent = 0.0,
                    color = Colors.RED)
            }
        }
        cellBorderColor = Colors.PINK
        cellColor = Colors.TRANSPARENT_WHITE


        cellIndent =  (boardSize - ((cellSize) * columnCellCount)) /
                (columnCellCount + 1)

        println("cell Indent is $cellIndent, cell Size is $cellSize, board Size is $boardSize")
        makeCells()
    }

    fun makeCells() {
        for (i in 0 until rowCellCount) {
            for(j in 0 until columnCellCount) {
                val leftCellIndent = leftIndent + cellIndent * (j + 1)
                val topCellIndent = topIndent + cellIndent * (i + 1)

                cells[i][j] = Cell(cellSize, i, j,
                    leftCellIndent, topCellIndent,
                    cellColor, cellBorderColor, cellBorderThickness)
            }
        }
    }

    fun getDrawableBoard() : RoundRect {
        return drawableBoard
    }

    fun getDrawableCells(): Array<Array<RoundRect>> {
        val drawableCells: Array<Array<RoundRect>> = Array(rowCellCount) {
            Array(columnCellCount) {
                RoundRect(0.0,0.0,0.0,0.0)
            }
        }

        for (i in 0 until rowCellCount) {
            for (j in 0 until columnCellCount) {
                drawableCells[i][j] = cells[i][j].getDrawableCell()
            }
        }
        return drawableCells
    }
}