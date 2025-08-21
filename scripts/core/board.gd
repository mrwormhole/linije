class_name Board
extends Node2D

@export var row_count: int = 8
@export var col_count: int = 9
@export var x_start: float = 64
@export var y_start: float = 200

const MAX_WIDTH: int = 576 # 9 * 64
const MAX_HEIGHT: int = 1024 # 16 * 64
const CELL_SIZE: float = 32.0
const INDICATOR_SIZE: float = 32.0

const cell_resource: Resource = preload("res://scenes/cell.tscn")
var all_cells: Array = []

const indicator_resource: Resource = preload("res://scenes/indicator.tscn")
var all_indicators: Array = []

func _ready():
	randomize()
	generate()

# generate() creates all cells with random generated cell state excluding empty cell state
func generate() -> void:
	all_cells = init_cells(row_count, col_count)
	for i in range(all_cells.size()):
		for j in range(all_cells[i].size()):
			all_cells[i][j].generated_state = States.rand_cell_state()
	all_indicators = init_indicators(row_count + 1, col_count + 1)
	
	# pp_cells()
	# pp_indicators()
	
	print("estimated total moves:", estimated_total_moves()) # current estimated total is not exact total
	get_tree().call_group("cells", "appear")
	get_tree().call_group("indicators", "appear")

	# so to figure out exact total moves, equation is simple,
	# go for each square and understand each line
	# each line can intersect with 2 indicators or 1 indicator

	# if it is intersecting with 2 indicators, we need to substract its value from the total to disqualify duplicate values in our total
	# if it is instersecting with 1 indicator, we keep total as it is

	# algorithm can be re-written from scratch to find lines that only intersect with indicators
	# then if it is intersecting, we can just add to the full total then we go to the next line
	# the challenge here is understanding if line is colliding or not

	# however if you don't wanna re-write the algorihm, you still need to understand which lines are colliding with indicators, code needs to change, I suppose algorithm re-write is necessary

# pp_cells is used for debugging purposes
func pp_cells() -> void:
	print("-".repeat(40))
	for row in all_cells:
		var row_str: String = ""
		for cell in row:
			row_str += "[{0}] ".format([cell.generated_state])
		print()
		print(row_str)
	print("-".repeat(40))
	
# pp_indicators is used for debugging purposes
func pp_indicators() -> void:
	print("-".repeat(40))
	for row in all_indicators:
		var row_str: String = ""
		for indicator in row:
			if indicator == null:
				row_str += "[N] "
			else:
				row_str += "[{0}] ".format(indicator.unlock_value)
		print()
		print(row_str)
	print("-".repeat(40))

func estimated_total_moves() -> int:
	var total_moves: int = 0
	for row in all_cells:
		for cell in row:
			if cell.is_effective():
				total_moves += cell.generated_state
				cell.generated_state = 0
	return total_moves
	
func make_2d_array(rows: int, cols: int) -> Array:
	var arr: Array = []
	for i in range(rows):
		arr.append([])
		for _j in range(cols):
			arr[i].append(null)
	return arr

# init_cells inits the cells
func init_cells(rows: int, cols: int) -> Array:
	var cells: Array = make_2d_array(rows, cols)
	for i in range(rows):
		for j in range(cols):
			var cell: Cell = cell_resource.instantiate()
			cell.color = Themes.current_theme["cell"]
			cell.size = CELL_SIZE
			cell.scale = Vector2(0, 0)
			var offset: float = calculate_offset(CELL_SIZE)
			cell.transform.origin.x = x_start + (j * offset) + (CELL_SIZE / 2)
			cell.transform.origin.y = y_start + (i * offset) + (CELL_SIZE / 2)
			cell.id = "{0}|{1}".format([i, j])
			cells[i][j] = cell
			add_child(cell)
	return cells

# init_indicatos inits the indicators
func init_indicators(rows: int, cols: int) -> Array:
	var indicators: Array = make_2d_array(rows, cols)
	for i in range(1, rows-1):
		for j in range(1, cols-1):
			if randi() % 10 > 4: # simulate 50% chance(difficult) to spawn
				continue
			var indicator: Indicator = indicator_resource.instantiate()
			indicator.size = INDICATOR_SIZE
			indicator.self_modulate = Color(self_modulate, 0)
			var offset: float = calculate_offset(INDICATOR_SIZE)
			var alignment: float = offset - INDICATOR_SIZE
			indicator.transform.origin.x = x_start - alignment/2 + (j * offset) + (INDICATOR_SIZE / 2)
			indicator.transform.origin.y = y_start - alignment/2 + (i * offset) + (INDICATOR_SIZE / 2)
			indicator.id = "{0}|{1}".format([i - 1, j - 1])
			indicators[i - 1][j - 1] = indicator
			add_child(indicator)
	return indicators
	
func calculate_offset(block_size: float) -> float:
	var offset: float = (MAX_WIDTH - 2 * x_start - block_size * col_count) / (col_count - 1)
	offset += block_size
	return offset

# all_indicators_unlocked compares all the current values of indicators against unlock values of indicators
func all_indicators_unlocked() -> bool:
	for row in all_indicators:
		for indicator in row:
			if indicator == null:
				continue
			if !indicator.is_unlocked():
				return false
	return true

# count_alive_indicators counts the non-null indicators
func count_alive_indicators() -> int:
	var existing_indicator_count: int = 0
	for row in all_indicators:
		for indicator in row:
			if indicator != null:
				existing_indicator_count += 1
	return existing_indicator_count

# _on_cell_touch_occurred is a function that broadcasts the cell_id for nearby indicators
func _on_cell_touch_occurred(cell_id: String) -> void:
	# perhaps if I know the cell_id, I can call selected indicators here explicitly rather than calling all indicators???
	get_tree().call_group("indicators", "look_around_for_cells", cell_id)
