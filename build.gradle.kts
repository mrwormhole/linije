import com.soywiz.korge.gradle.*

buildscript {
	val korgePluginVersion: String by project

	repositories {
		mavenLocal()
		mavenCentral()
		google()
		maven { url = uri("https://plugins.gradle.org/m2/") }
	}
	dependencies {
		classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
	}
}

apply<KorgeGradlePlugin>()

korge {
	id = "com.goldenhandsoftware.linije"
	version = "0.0.1"
	exeBaseName = "linije"
	name = "linije"
	orientation = Orientation.PORTRAIT

	authorName = "Talha Altinel"
	authorEmail = "talhaaltinel@hotmail.com"
	authorHref = "https://goldenhandsoftware.co.uk"
	author(authorName, authorEmail, authorHref)

	//icon = File(rootDir, "icon.png")

	gameCategory = GameCategory.PUZZLE
	fullscreen = true
	entryPoint = "main"
// To enable all targets at once

	//targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets

	targetJvm()
	targetJs()
	targetDesktop()
	targetIos()
	targetAndroidIndirect() // targetAndroidDirect()
}