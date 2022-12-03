package gwent.core.serialize

/**
 * A data transfer object containing game data about a game.
 * Intended as intermediary object in (de)serializable.
 */
class GameDTO(
    val deck: List<CardDTO>,
    val players: List<PlayerDTO>,
    val currentPlayer: Int,
    val round: Int,
)