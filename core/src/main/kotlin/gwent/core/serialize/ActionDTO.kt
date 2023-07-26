package gwent.core.serialize

import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import gwent.core.game.*
import kotlin.reflect.KClass

/**
 * The [ActionTypeAdapter] is used to determine the subclass of [ActionDTO].
 * This is done by checking the `type` field of [ActionDTO] and guiding Klaxon to right class.
 */
class ActionTypeAdapter : TypeAdapter<ActionDTO> {
    override fun classFor(type: Any): KClass<out ActionDTO> = when (type as String) {
        ActionDTO.PASS_TYPE -> PassDTO::class
        ActionDTO.PLAY_CARD_TYPE -> PlayCardDTO::class
        else -> throw IllegalArgumentException("Unknown action type: $type")
    }
}

/**
 * A data transfer object containing data about an action.
 * Intended as intermediary object in (de)serializable.
 */
@TypeFor(field = "type", adapter = ActionTypeAdapter::class)
sealed class ActionDTO(
    /**
     * This field used by [ActionTypeAdapter] to determine the subclass of the [ActionDTO].
     */
    val type: String,
    val player: Int,
) {
    abstract fun toAction(): Action

    companion object {
        // The name used to identify each subclass in json
        const val PASS_TYPE = "pass"
        const val PLAY_CARD_TYPE = "play-card"
    }
}

/**
 * A data transfer object containing data about a play-card action.
 * Intended as intermediary object in (de)serializable.
 */
class PlayCardDTO(
    player: Int,
    val cardName: String,
    val row: String?,
) : ActionDTO(PLAY_CARD_TYPE, player) {
    override fun toAction() = PlayCard(player, Card.fromName(cardName), row?.let { RowSuit.fromName(row) })
}

/**
 * A data transfer object containing data about a pass action.
 * Intended as intermediary object in (de)serializable.
 */
class PassDTO(player: Int) : ActionDTO(PASS_TYPE, player) {
    override fun toAction() = Pass(player)
}
