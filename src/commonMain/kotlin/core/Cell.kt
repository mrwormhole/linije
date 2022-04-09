package core

import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.*
import com.soywiz.korge.view.tween.rotateBy
import com.soywiz.korge.view.tween.rotateTo
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.geom.plus

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
    val drawable: RoundRect
    lateinit var drawableInner: Line
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
        drawable.onClick {
            changeStateOnClick()

            //drawableInner.rotation = drawableInner.rotation.plus((90).degrees)
            //drawableInner.x += size / 2
            //drawableInner.y += size / 2
            drawableInner.rotateBy((90).degrees)
            //drawableInner.x -= size / 2
            //drawableInner.y -= size / 2

            println("rot: drawableInnerRotation ${drawableInner.rotation}")
        }
    }

    fun changeStateOnClick() {
        state = when (state){
            CellState.EMPTY -> CellState.RIGHT_LINE_DRAWN
            CellState.RIGHT_LINE_DRAWN -> CellState.LEFT_LINE_DRAWN
            CellState.LEFT_LINE_DRAWN -> CellState.RIGHT_LINE_DRAWN
        }
    }

    fun createInnerLine() {
        drawableInner = Line(0.0, 0.0, 0.0, 0.0, Colors.RED).apply {
            x1 = drawable.x
            y1 = drawable.y
            x2 = drawable.x + size
            y2 = drawable.y + size
        }
    }
}