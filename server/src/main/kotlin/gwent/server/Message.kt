package gwent.server

import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import gwent.core.serialize.ActionDTO
import gwent.core.serialize.GameDTO
import kotlin.reflect.KClass

/**
 * The [MessageTypeAdapter] is used to determine the subclass of [Message].
 * This is done by checking the `type` field of [Message] and guiding Klaxon to right class.
 */
class MessageTypeAdapter : TypeAdapter<Message> {
    override fun classFor(type: Any): KClass<out Message> = when (type as String) {
        Message.REQUEST_GAME_STATE -> GetGameStateMsg::class
        Message.GAME_STATE -> GameStateMsg::class
        Message.RESTART_GAME -> RestartGameMsg::class
        Message.ACTION -> ActionMsg::class
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
        const val COMMUNICATION_ERROR = "communication-error"
        const val REQUEST_GAME_STATE = "request-game-state"
        const val GAME_STATE = "game-state"
        const val RESTART_GAME = "restart-game"
        const val ACTION = "action"
        const val INVALID_ACTION = "invalid-action"
    }
}

/**
 * Sent by client to request the current game state.
 */
class GetGameStateMsg : Message(REQUEST_GAME_STATE)

/**
 * Sent by server to update a client about the current game state.
 */
class GameStateMsg(
    val game: GameDTO?,
) : Message(GAME_STATE)

/**
 * Sent by client to request a restart of the game.
 */
class RestartGameMsg(
    val player1Name: String,
    val player2Name: String,

    /**
     * If true, a new game will be started, even if a game is already running.
     */
    val force: Boolean,
) : Message(RESTART_GAME)

/**
 * Sent by client when the player wants to take an action.
 */
class ActionMsg(
    val action: ActionDTO,
) : Message(ACTION)

/**
 * Sent by server when player attempts to take an invalid action.
 */
class InvalidActionMsg(
    val details: String,
) : Message(INVALID_ACTION)

/**
 * Sent when an incoming message was not a valid message.
 */
class CommunicationErrorMsg(
    val details: String,
) : Message(COMMUNICATION_ERROR)