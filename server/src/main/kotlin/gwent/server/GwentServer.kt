package gwent.server

import gwent.core.game.Game
import gwent.core.serialize.gwentKlaxonSetup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class GwentServer {

    fun start(port: Int): Nothing {
        val game = Game("Alice", "Bob")
        val serverSocket = ServerSocket(port)
        println("Gwent server started on port $port. Waiting for clients...")

        // This is the main loop of the server.
        // We wait for somebody to connect and when they do, we create a thread that handles communication with them.
        while (true) GwentClientHandler(serverSocket.accept(), game).start()
    }
}

/**
 * The [GwentClientHandler] is a thread that handles communication with a single client.
 */
private class GwentClientHandler(
    private val clientSocket: Socket,
    private val game: Game,
) : Thread() {

    lateinit var out: PrintWriter
    lateinit var `in`: BufferedReader

    override fun run() {
        print("Client connected. ")
        out = PrintWriter(clientSocket.getOutputStream(), true)
        `in` = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

        println("Sending game to client...")
        out.println(gwentKlaxonSetup().toJsonString(game.toDTO()))
        out.flush()

        // The main loop of the client handler
        while (true) {
            val inputLine = `in`.readLine()
            if ("close" == inputLine) {
                out.println("bye")
                break
            }
            out.println("OK")
        }
        `in`.close()
        out.close()
        clientSocket.close()
        println("Client handler stopped.")
    }
}