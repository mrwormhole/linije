extends Area2D

const screen_offset: int = 64

@onready var up: CollisionShape2D = get_node("up")
@onready var down: CollisionShape2D = get_node("down")
@onready var left: CollisionShape2D = get_node("left")
@onready var right: CollisionShape2D = get_node("right")

func _ready():
	var screen_size: Vector2 = get_viewport().size
	var topleft: Vector2 = Vector2(-screen_offset, -screen_offset)
	var topright: Vector2 = Vector2(screen_size.x + screen_offset, -screen_offset)
	var downleft: Vector2 = Vector2(-screen_offset, screen_size.y + screen_offset)
	var downright: Vector2 = Vector2(screen_size.x + screen_offset, screen_size.y + screen_offset)
	
	up.shape.set("a", topleft)
	up.shape.set("b", topright)
	down.shape.set("a", downleft)
	down.shape.set("b", downright)
	left.shape.set("a", topleft)
	left.shape.set("b", downleft)
	right.shape.set("a", topright)
	right.shape.set("b", downright)

func _on_body_entered(body):
	if body.is_in_group("indicators"):
		body.queue_free()
