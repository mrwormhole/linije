package core

import com.soywiz.korge.view.Circle
import com.soywiz.korge.view.Text
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

class Indicator(
    radius: Double = 16.0,
    color: RGBA = Colors.BLUE,
    borderColor: RGBA = Colors.BLACK,
    borderThickness: Double = 2.0
) {
    //val text: Text
    val drawable: Circle
    private val radius: Double
    private val color: RGBA
    private val borderColor: RGBA
    private val borderThickness: Double

    init {
        this.radius = radius
        this.color = color
        this.borderColor = borderColor
        this.borderThickness = borderThickness

        drawable = Circle(radius, color, borderColor, borderThickness)
        //text = Text()
    }
}