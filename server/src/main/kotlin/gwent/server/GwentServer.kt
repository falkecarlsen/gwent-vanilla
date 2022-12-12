package gwent.server

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration

/**
 * The server uses the Ktor framework with netty engine.
 * The actual entry point (setup of our stuff) is [Application.module].
 */
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

/**
 * Sets up the GwentServer.
 */
@Suppress("Unused")
fun Application.module() {
    configureSockets()
    println("Hello from Gwent Server")
}

/**
 * Sets up the WebSocket and routes.
 */
fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        // This block runs when a socket connects to our server using the '/gwent' route
        webSocket("/gwent") {
            println("Somebody connected!")
            send("You are connected!")
            flush()
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                send("You said: $receivedText")
                flush()
            }
        }
    }
}