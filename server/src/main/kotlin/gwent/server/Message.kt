package gwent.server

import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import gwent.core.serialize.GameDTO
import kotlin.reflect.KClass

/**
 * The [MessageTypeAdapter] is used to determine the subclass of [Message].
 * This is done by checking the `type` field of [Message] and guiding Klaxon to right class.
 */
class MessageTypeAdapter : TypeAdapter<Message> {
    override fun classFor(type: Any): KClass<out Message> = when (type as String) {
        Message.GET_GAME_STATE -> GetGameStateMsg::class
        Message.GAME_STATE -> GameStateMsg::class
        Message.RESTART_GAME -> RestartGameMsg::class
        else -> throw IllegalArgumentException("Unknown action type: $type")
    }
}

/**
 * [Message] is super class for all messages exchanged between the server and the clients.
 */
@TypeFor(field = "type", adapter = MessageTypeAdapter::class)
sealed class Message(
    /**
     * This field used by [MessageTypeAdapter] to determine the subclass of the [Message].
     */
    val type: String,
) {
    companion object {
        const val GET_GAME_STATE = "get-game-state"
        const val GAME_STATE = "game-state"
        const val RESTART_GAME = "restart-game"
    }
}

/**
 * Sent by client to request the current game state.
 */
class GetGameStateMsg : Message(GET_GAME_STATE)

/**
 * Sent by server to update a client about the current game state.
 */
class GameStateMsg(
    val game: GameDTO,
) : Message(GAME_STATE)

/**
 * Sent by client to request a restart of the game.
 */
class RestartGameMsg(
    val player1Name: String,
    val player2Name: String,
) : Message(RESTART_GAME)
