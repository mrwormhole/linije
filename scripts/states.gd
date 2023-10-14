extends Node

# Cell States
enum {
	EMPTY,     # [ ]
	LEFT_LINE, # [\]
	RIGHT_LINE # [/]
}

# Indicator States
enum {
	UNKNOWN,
	LOCKED,
	UNLOCKED,
}

func rand_cell_state() -> int:
	return [LEFT_LINE, RIGHT_LINE][randi() % 2] 
	# empty is excluded to produce less 0 unlock value indicators
