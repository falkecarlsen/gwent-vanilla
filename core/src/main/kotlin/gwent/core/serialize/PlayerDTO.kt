package gwent.core.serialize

/**
 * A data transfer object containing game data about a player.
 * Intended as intermediary object in (de)serializable.
 */
class PlayerDTO(
    val index: Int,
    val name: String,
    val roundsWon: Int,
    val hand: List<CardDTO>,
    val board: PlayerBoardDTO,
    val hasPassed: Boolean,
)
