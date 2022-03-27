package core

import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.RoundRect
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

class Cell(size: Double = 64.0,
           i: Int,
           j: Int,
           leftIndent: Double,
           topIndent: Double,
           color: RGBA = Colors.TRANSPARENT_WHITE,
           borderColor: RGBA = Colors.BLACK,
           borderThickness: Double = 2.0
) {
    private val cell: RoundRect
    private val size: Double
    private val rx: Double = 5.0
    private val ry: Double = 1.0
    private val color: RGBA
    private val borderColor: RGBA
    private val borderThickness: Double

    init {
        this.size = size
        this.color = color
        this.borderColor = borderColor
        this.borderThickness = borderThickness

        cell = RoundRect(size, size, rx, ry,
            color, borderColor, borderThickness).apply {
                name = "Cell ${i}${j}"
                x = size * j + leftIndent
                y = size * i + topIndent

                onClick {
                    this.color = Colors.GREEN
                    println("Clicked on $name")
                }
            }
    }

    fun getDrawableCell(): RoundRect {
        return cell
    }
}