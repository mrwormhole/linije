package core

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

enum class CellState {
    EMPTY,
    RIGHT_LINE_DRAWN,
    LEFT_LINE_DRAWN,
}

class Cell(size: Double = 64.0,
           color: RGBA = Colors.PINK,
           borderColor: RGBA = Colors.TRANSPARENT_WHITE,
           borderThickness: Double = 2.0
) {
    val text: Text
    val drawable: RoundRect
    private val size: Double
    private val rx: Double = 5.0
    private val ry: Double = 1.0
    private val color: RGBA
    private val borderColor: RGBA
    private val borderThickness: Double
    private var state: CellState

    init {
        this.size = size
        this.color = color
        this.borderColor = borderColor
        this.borderThickness = borderThickness
        this.state = CellState.EMPTY

        drawable = RoundRect(size, size, rx, ry, color, borderColor, borderThickness)
        text = Text("X", 48.0, Colors.WHITE, AssetLoader.font)
    }
}