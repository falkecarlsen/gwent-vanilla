package gwent.core.serialize

import gwent.core.game.Suit

/**
 * A data transfer object containing game data about a card.
 * Intended as intermediary object in (de)serializable.
 */
class CardDTO(
    val name: String,
    val basePower: Int,
    val suit: Suit,
)
