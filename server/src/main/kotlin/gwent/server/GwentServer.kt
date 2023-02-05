package gwent.server

import gwent.core.GameManager
import gwent.core.GameStateChangeListener
import gwent.core.game.Game
import gwent.core.game.InvalidActionException
import gwent.core.game.InvalidCardNameException
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

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
) : Thread(), GameStateChangeListener {

    val messenger = Messenger(clientSocket)

    init {
        gameManager.registerListener(this)
    }

    override fun run() {
        print("Client connected. ")
        try {
            println("Sending game to client...")
            messenger.sendGameState(gameManager.game)

            // The main loop of the client handler
            while (true) {
                try {
                    val msg = messenger.receive()
                    handleMessage(msg)
                } catch (err: MessengerParsingException) {
                    messenger.sendCommunicationError("Failed to parse json")
                } catch (err: SocketException) {
                    println("Connection lost")
                    break
                }
            }
        } finally {
            messenger.close()
            clientSocket.close()
            gameManager.unregisterListener(this)
        }
        println("Client handler stopped")
    }

    private fun handleMessage(msg: Message) {
        when (msg) {
            is GetGameStateMsg -> messenger.sendGameState(gameManager.game)
            is ActionMsg -> {
                if (!gameManager.doesGameExist()) {
                    // Notify client that no game is running
                    messenger.sendGameState(gameManager.game)
                    return
                }
                println("Received action from player")
                var fail = false
                try {
                    val action = msg.action.toAction()
                    // TODO: Check if `action.player` matches the player of this client

                    // All clients are notified if the action successfully modifies the game state
                    gameManager.tryPerformAction(action) // FIXME: Possible race condition?

                } catch (err: InvalidCardNameException) {
                    // Message could not be parsed due to use of invalid card names
                    messenger.sendCommunicationError("Invalid card name '${err.invalidName}'")
                    println("Invalid card name '${err.invalidName}'")
                    fail = true

                } catch (err: InvalidActionException) {
                    // Action could not be performed
                    messenger.sendInvalidAction(err.reason)
                    println("Invalid action: ${err.reason}")
                    fail = true
                }
                if (!fail) println("Action successfully performed")
            }
            is RestartGameMsg -> {
                gameManager.tryStartNewGame(msg.player1Name, msg.player2Name, msg.force)
                messenger.sendGameState(gameManager.game)
            }
            else -> TODO("Handle message of type ${msg::class}")
        }
    }

    override fun onGameStateChange(game: Game) {
        messenger.sendGameState(game)
    }
}