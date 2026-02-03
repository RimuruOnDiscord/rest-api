import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaParserSource
import org.koitharu.kotatsu.parsers.MangaSourceConfig

class WebMangaContext : MangaLoaderContext {
    override val httpClient: OkHttpClient = OkHttpClient.Builder().build()
    override val cookieJar: CookieJar = CookieJar.NO_COOKIES
    
    override fun getParserConfig(source: MangaParserSource): MangaSourceConfig {
        return object : MangaSourceConfig {}
    }
}
