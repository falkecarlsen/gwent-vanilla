package gwent.core.serialize

/**
 * A data transfer object containing game data about a player's board.
 * Intended as intermediary object in (de)serializable.
 */
class PlayerBoardDTO(
    val currentPower: Int,
    val spades: RowDTO,
    val clubs: RowDTO,
    val diamonds: RowDTO,
)
