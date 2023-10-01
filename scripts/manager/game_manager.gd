extends Node2D

const board_resource: Resource = preload("res://scenes/board.tscn")

var stage_complete: bool = false
var stage: int = 1
var previous_board: Board = null
var current_board: Board = null

func _ready() -> void:
	RenderingServer.set_default_clear_color(Themes.current_theme["background"])
	var board: Board = board_resource.instantiate()
	add_child(board)
	current_board = board

func _process(delta: float) -> void:
	# kickstart a stage
	if (!stage_complete && current_board.all_indicators_unlocked()) || Input.is_action_just_pressed("enter"):
		stage_complete = true
		previous_board = current_board
		get_tree().call_group("indicators", "disappear")
		get_tree().call_group("cells", "disappear")
		
	# prune a stage then kickstart a stage
	if previous_board != null && previous_board.count_alive_indicators() == 0:
		previous_board.queue_free()
		previous_board = null
		stage += 1
		
		var board: Board = board_resource.instantiate()
		add_child(board)
		current_board = board
		stage_complete = false

