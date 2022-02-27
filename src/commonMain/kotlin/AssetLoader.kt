import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

object AssetLoader {
    lateinit var myBitmap: Bitmap

    suspend fun load() {
        myBitmap = resourcesVfs["korge.png"].readBitmap()
        //inspired by here https://sourcegraph.com/github.com/LeHaine/ld48-sweet-dreams/-/blob/src/commonMain/kotlin/com/lehaine/game/Assets.kt?L121
    }
}