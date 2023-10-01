extends Node

var themes: Dictionary = {
	"light_theme": {
		"background": Color("#FFFFFF"),
		"cell": Color("#787C80"),
		"font": Color("#000000"),
	},
	"dark_theme": {
		"background": Color("#121212"),
		"cell": Color("#3A3A3A"),
		"font": Color("#EDEDED"),
	},
}

var current_theme = themes["light_theme"]
