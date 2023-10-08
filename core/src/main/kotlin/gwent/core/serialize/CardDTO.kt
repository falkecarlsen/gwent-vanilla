package gwent.core.serialize

/**
 * A data transfer object containing game data about a card.
 * Intended as intermediary object in (de)serializable.
 */
class CardDTO(
    val type: String,
    val currentPower: Int,
)
