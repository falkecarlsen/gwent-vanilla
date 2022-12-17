package gwent.server

import gwent.core.game.Game
import gwent.core.serialize.gwentKlaxonSetup
import java.io.BufferedReader
import java.io.Closeable
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

/**
 * The [Messenger] class is a mediator for communicating over a socket using [Message]s.
 * It handles the serialization and deserialization of relevant data and provides methods
 * for sending all relevant [Message]s.
 * To receive a message, call [receive].
 */
class Messenger(
    socket: Socket,
) : Closeable {

    private val out = PrintWriter(socket.getOutputStream(), true)
    private val `in` = BufferedReader(InputStreamReader(socket.getInputStream()))
    private val klx = gwentKlaxonSetup()

    /**
     * Receive the next [Message] from the given socket.
     * This is blocking.
     */
    fun receive(): Message {
        val json = `in`.readLine()
        return klx.parse<Message>(json) ?: throw MessengerParsingException(json)
    }

    fun sendGameState(game: Game) {
        val json = klx.toJsonString(GameStateMsg(game.toDTO()))
        out.println(json)
        out.flush()
    }

    override fun close() {
        out.close()
        `in`.close()
    }
}

class MessengerParsingException(msg: String) : RuntimeException("Unable to parse incoming message: $msg")