class_name Line
extends Node2D

const interpolation_seconds: float = 0.3
const thickness: float = 3.0

var size: float
var color: Color
var draw_start_point: Vector2
var draw_end_point: Vector2
var is_clickable: bool

@onready var cell: Cell = get_parent()
@onready var audio: AudioStreamPlayer = get_node("audio")

func _ready():
	size = cell.size
	color = cell.color
	draw_start_point = Vector2(-size/2, -size/2)
	draw_end_point = Vector2(size/2, size/2)
	is_clickable = true

func _draw() -> void:
	if cell.current_state != States.EMPTY:
		draw_line(draw_start_point, draw_end_point, color, thickness)

func _on_cell_input_event(_viewport: Node, event: InputEvent, _shape_idx: int) -> void:
	if event is InputEventScreenTouch and event.is_pressed():
		if !is_clickable:
			return
		is_clickable = false
		audio.play()
		var tween: Tween = get_tree().create_tween().set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_IN_OUT)
		
		if cell.current_state == States.EMPTY:
			cell.change_state()
			queue_redraw()
			tween.tween_property(self, "scale", Vector2(1, 1), interpolation_seconds)
			tween.tween_callback(enable_click)
			return
		
		cell.change_state()
		var next_rotation: float = self.rotation + deg_to_rad(90)
		tween.tween_property(self, "rotation", next_rotation, interpolation_seconds)
		tween.tween_callback(enable_click)

func enable_click() -> void:
	is_clickable = true
	
