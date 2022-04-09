package core

import com.soywiz.korge.view.Circle
import com.soywiz.korge.view.Text
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

enum class IndicatorState {
    UNKNOWN,
    UNLOCKED,
    LOCKED
}

class Indicator(
    radius: Double = 16.0,
    color: RGBA = Colors.BLUE,
    borderColor: RGBA = Colors.BLACK,
    borderThickness: Double = 2.0
) {
    val text: Text
    val drawable: Circle
    private val radius: Double
    private val color: RGBA
    private val borderColor: RGBA
    private val borderThickness: Double
    private var state: IndicatorState
    private var keyValue: Byte
    private var value: Byte

    init {
        this.radius = radius
        this.color = color
        this.borderColor = borderColor
        this.borderThickness = borderThickness
        this.state = IndicatorState.UNKNOWN
        this.value = 0
        this.keyValue = 0

        drawable = Circle(radius, color, borderColor, borderThickness)
        text = Text((0..4).random().toString(), 48.0, Colors.WHITE, AssetLoader.font)
    }
}