package gwent.server

import gwent.core.GameManager
import gwent.core.game.Game
import gwent.core.serialize.gwentKlaxonSetup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class GwentServer {

    fun start(port: Int): Nothing {
        val gameManager = GameManager()
        gameManager.tryStartNewGame("Alice", "Bob", true)

        val serverSocket = ServerSocket(port)
        println("Gwent server started on port $port. Waiting for clients...")

        // This is the main loop of the server.
        // We wait for somebody to connect and when they do, we create a thread that handles communication with them.
        while (true) GwentClientHandler(serverSocket.accept(), gameManager).start()
    }
}

/**
 * The [GwentClientHandler] is a thread that handles communication with a single client.
 */
private class GwentClientHandler(
    private val clientSocket: Socket,
    private val gameManager: GameManager,
) : Thread() {

    val messenger = Messenger(clientSocket)

    override fun run() {
        print("Client connected. ")
        try {
            println("Sending game to client...")
            messenger.sendGameState(gameManager.game)

            // The main loop of the client handler
            while (true) {
                val msg = messenger.receive()
                when (msg) {
                    is GetGameStateMsg -> messenger.sendGameState(gameManager.game)
                    is RestartGameMsg -> {
                        gameManager.tryStartNewGame(msg.player1Name, msg.player2Name, msg.force)
                        messenger.sendGameState(gameManager.game)
                    }
                    else -> TODO("Handle message of type ${msg::class}")
                }
            }
        } finally {
            messenger.close()
            clientSocket.close()
        }
        println("Client handler stopped.")
    }
}