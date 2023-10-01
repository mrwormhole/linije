class_name Indicator
extends RigidBody2D

const blue: Color = Color("#85C0F9")
const orange: Color = Color("#F5793A")
const interpolation_seconds: float = 0.6

var id: String
var size: float
var color: Color
var locked_color: Color
var unlocked_color: Color
var draw_point: Vector2
var current_state: int
var previous_state: int
var current_value: int
var unlock_value: int

@onready var label: Label = get_node("label")
@onready var audio: AudioStreamPlayer = get_node("audio") 
@onready var shape: CollisionShape2D = get_node("shape")
@onready var board: Board = get_parent() 

func _ready() -> void:	
	current_state = States.UNKNOWN
	current_value = 0
	self.transform.origin += Vector2(-size/2, -size/2)
	locked_color = blue
	unlocked_color = orange
	set_unlock_value()
	update_appearance()
	
func _draw() -> void:
	draw_circle_arc(Vector2(0, 0), size/2, 0, 360, color)
	
func draw_circle_arc(center: Vector2, radius: float, angle_from: float, angle_to: float, color: Color) -> void:
	const point_count: int = 500
	var points_arc: PackedVector2Array = [center]
	
	for i in range(point_count + 1):
		var angle_point = deg_to_rad(angle_from + i * (angle_to - angle_from) / point_count - 90)
		points_arc.push_back(center + Vector2(cos(angle_point), sin(angle_point)) * radius)
	draw_polygon(points_arc, PackedColorArray([color]))

func is_unlocked() -> bool:
	return current_value == unlock_value
	
func update_appearance() -> void:
	label.text = str(unlock_value)
	previous_state = current_state
	if is_unlocked():
		color = unlocked_color
		current_state = States.UNLOCKED
		if previous_state != current_state && previous_state != States.UNKNOWN:
			audio.play()
	else:
		color = locked_color
		current_state = States.LOCKED
	
	if previous_state != current_state:
		queue_redraw()

# look_around_for_cells is an optimized method for indicators to check current values against lock values
func look_around_for_cells(cell_id: String) -> void:
	var cellID: PackedStringArray = cell_id.split("|")
	var cell_i: int = int(cellID[0])
	var cell_j: int = int(cellID[1])
	
	var indicatorID: PackedStringArray = id.split("|")
	var indicator_i: int = int(indicatorID[0])
	var indicator_j: int = int(indicatorID[1])
	
	# tight optimization to ensure wew only check local cells
	if cell_i - indicator_i != 0 && cell_i - indicator_i != 1:
		return
	if cell_j - indicator_j != 0 && cell_j - indicator_j != 1:
		return
		
	var cells = board.all_cells
	current_value = 0
	if check_cell(cells[indicator_i][indicator_j], States.LEFT_LINE):
		current_value += 1
	if check_cell(cells[indicator_i + 1][indicator_j], States.RIGHT_LINE):
		current_value += 1
	if check_cell(cells[indicator_i][indicator_j + 1], States.RIGHT_LINE):
		current_value += 1
	if check_cell(cells[indicator_i + 1][indicator_j + 1], States.LEFT_LINE):
		current_value += 1
	update_appearance()
	
func check_cell(cell: Cell, wanted_state: int) -> bool:
	return cell.current_state == wanted_state

func set_unlock_value() -> void:
	var indicatorID: PackedStringArray = id.split("|")
	var i : int = int(indicatorID[0])
	var j : int = int(indicatorID[1])
	
	var cells = board.all_cells
	var new_unlock_value: int = 0
	if check_generated_cell(cells[i][j], States.LEFT_LINE):
		new_unlock_value += 1
	if check_generated_cell(cells[i+1][j], States.RIGHT_LINE):
		new_unlock_value += 1
	if check_generated_cell(cells[i][j+1], States.RIGHT_LINE):
		new_unlock_value += 1
	if check_generated_cell(cells[i+1][j+1], States.LEFT_LINE):
		new_unlock_value += 1
	unlock_value = new_unlock_value
	
func check_generated_cell(cell: Cell, wanted_state: int) -> bool:
	return cell.generated_state == wanted_state

# appear is where we play the animation to make it appear
func appear() -> void:
	var tween: Tween = get_tree().create_tween().set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_IN_OUT)
	tween.tween_property(self, "self_modulate:a", 1, 0.9)
	tween.tween_property(self, "rotation", self.rotation + deg_to_rad(360), interpolation_seconds)
	tween.parallel().tween_method(override_font_size, 1, 16, interpolation_seconds)

func override_font_size(font_size: int) -> void:
	label.add_theme_font_size_override("font_size", font_size)

# disappear is where we play the animation to make it disappear
func disappear() -> void:
	var tween: Tween = get_tree().create_tween().set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_IN_OUT)
	self.gravity_scale = 0.6
		
	var horizontal_impulse: int = randi_range(250,500)
	var vertical_impulse: int = randi_range(250,500)
	
	if randi() % 2:
		horizontal_impulse = -horizontal_impulse
	
	if randi() % 2:
		vertical_impulse = -vertical_impulse
	
	var x = randi_range(-size/2, size/2)
	var y = randi_range(-size/2, size/2)
	
	apply_impulse(Vector2(horizontal_impulse, vertical_impulse), Vector2(x, y).normalized())

func _to_string() -> String:
	return "x1: {0}, y1: {1}, size: {2}".format([self.x, self.y, size])
