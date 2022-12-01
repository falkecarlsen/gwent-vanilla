package gwent.core.serialize

/**
 * A data transfer object containing game data about a board row.
 * Intended as intermediary object in (de)serializable.
 */
class RowDTO(
    val suit: String,
    val currentPower: Int,
    val units: List<CardDTO>,
)
