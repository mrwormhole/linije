import com.soywiz.korge.scene.Module
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt

object LinijeModule : Module() {
    override val mainScene = IntroScene::class
    override val title = "Linije"
    override val bgcolor = Colors["#641398"]
    override val icon = null
    const val WIDTH = 480 * 2
    const val HEIGHT = 640 * 2
    override val size = SizeInt(WIDTH, HEIGHT)

    override suspend fun AsyncInjector.configure() {
        AssetLoader.load()
        mapSingleton {
            GameplayScene()
        }
        mapSingleton {
            IntroScene()
        }
    }
}