package gwent.core.serialize

/**
 * A data transfer object containing game data about a card.
 * Intended as intermediary object in (de)serializable.
 */
class CardDTO(
    val name: String,
    val numeric: Int,
    val basePower: Int,
    val suit: String,
    val currentPower: Int,
)
