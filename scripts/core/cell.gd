class_name Cell
extends Area2D

signal touch_occurred

const interpolation_seconds: float = 0.3
const thickness: float = 1.5

var id: String
var size: float
var color: Color
var draw_point: Vector2
var current_state: int # this is only used for clicks on the lines
var generated_state: int # this is only used for generate() of board

@onready var board: Board = get_parent()

func _ready():
	draw_point = Vector2(-size/2, -size/2)
	current_state = States.EMPTY
	connect(touch_occurred.get_name(), board._on_cell_touch_occurred)

func _draw():
	draw_rect(Rect2(draw_point, Vector2(size, size)), color, false, thickness)

# change_state is a function who changes every time someone clicks on the cell 
# and tweens the line inside the cell then sends a signal to the board
func change_state() -> void:
	match current_state:
		States.EMPTY:
			current_state = States.LEFT_LINE
		States.RIGHT_LINE:
			current_state = States.LEFT_LINE
		States.LEFT_LINE:
			current_state = States.RIGHT_LINE
	touch_occurred.emit(id)

# is_effective is used to understand if cell is required to be tapped before the user gameplay starts
func is_effective() -> bool:
	if generated_state == States.EMPTY:
		return false
		
	var cellIDs: PackedStringArray = id.split("|")
	var cell_i: int = int(cellIDs[0])
	var cell_j: int = int(cellIDs[1])
	
	var indicators: Array = board.all_indicators
	
	match generated_state:
		States.LEFT_LINE:
			if cell_i > 0 && cell_j > 0:
				var upper_left = indicators[cell_i - 1][cell_j - 1]
				if upper_left != null:
					return true
			
			var bottom_right = indicators[cell_i][cell_j]
			if bottom_right != null:
				return true
		States.RIGHT_LINE:
			if cell_i > 0:
				var upper_right = indicators[cell_i - 1][cell_j]
				if upper_right != null:
					return true
			
			if cell_j > 0:
				var bottom_left = indicators[cell_i][cell_j - 1]
				if bottom_left != null:
					return true
	return false

# appear is where we play the animation to make it appear
func appear() -> void:
	var tween: Tween = get_tree().create_tween().set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_IN_OUT)
	tween.tween_property(self, "scale", Vector2(1, 1), interpolation_seconds)

# disappear is where we play the animation to make it disappear
func disappear() -> void:
	var tween: Tween = get_tree().create_tween().set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_IN_OUT)
	tween.tween_property(self, "scale", Vector2(0, 0), interpolation_seconds)
	tween.tween_callback(queue_free)

func _to_string():
	return "x1: {0}, y1: {1}, size: {2}".format([self.x, self.y, size])
