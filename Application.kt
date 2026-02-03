import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*
import org.koitharu.kotatsu.parsers.MangaParserSource
import org.koitharu.kotatsu.parsers.model.MangaSearchQuery

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) { json() }
        
        // VERY IMPORTANT: Allows your Vercel frontend to talk to this backend
        install(CORS) {
            anyHost() 
            allowHeader(HttpHeaders.ContentType)
        }

        val context = WebMangaContext()

        routing {
            get("/search") {
                val queryText = call.parameters["q"] ?: ""
                val sourceParam = call.parameters["source"] ?: "MANGADEX"
                
                try {
                    val source = MangaParserSource.valueOf(sourceParam)
                    val parser = context.newParserInstance(source)
                    
                    // Fetch from the library
                    val results = parser.getList(MangaSearchQuery(queryText))
                    
                    // Map it to match your TypeScript "Manga" Interface
                    val response = results.map {
                        mapOf(
                            "id" to it.id,
                            "title" to it.name,
                            "coverUrl" to it.coverUrl,
                            "publicUrl" to it.publicUrl,
                            "source" to sourceParam
                        )
                    }
                    
                    call.respond(response)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
                }
            }
        }
    }.start(wait = true)
}