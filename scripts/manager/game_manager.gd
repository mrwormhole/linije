extends Node2D

const board_resource: Resource = preload("res://scenes/board.tscn")
const tada_sounds: Array[Resource] = [
	preload("res://audio/tada-1.wav"),
	preload("res://audio/tada-2.wav"),
	preload("res://audio/tada-3.wav"),
]

var stage_complete: bool = false
var stage: int = 1
var previous_board: Board = null
var current_board: Board = null
var previous_audio_index: int = -1

@onready var audio: AudioStreamPlayer = get_node("audio")

func _ready() -> void:
	randomize()
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
		play_tada_sound()

	# prune a stage then kickstart a stage
	if previous_board != null && previous_board.count_alive_indicators() == 0:
		previous_board.queue_free()
		previous_board = null
		stage += 1
		Input.vibrate_handheld(500)

		var board: Board = board_resource.instantiate()
		add_child(board)
		current_board = board
		stage_complete = false

func play_tada_sound() -> void:
	var current_audio_index: = randi() % 3
	while previous_audio_index == current_audio_index:
		current_audio_index = randi() % 3

	audio.stream = tada_sounds[current_audio_index]
	audio.play()
	previous_audio_index = current_audio_index

