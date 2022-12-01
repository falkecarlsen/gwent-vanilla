package gwent.core.game

import gwent.core.serialize.PlayerDTO

/**
 * A player and everything that belongs to a player.
 */
class Player(
    val index: Int,
    val name: String,
) {
    var roundsWon: Int = 0
    var hand: MutableList<Card> = mutableListOf()
    val board: PlayerBoard = PlayerBoard()
    var hasPassed: Boolean = false

    /**
     * Clear this player's board and mark [hasPassed] as false.
     */
    fun prepareForNewRound() {
        hasPassed = false
        for ((suit, row) in board.rows) {
            row.cards.clear()
        }
    }

    /**
     * Convert to data transfer object.
     */
    fun toDTO() = PlayerDTO(
        index = index,
        name = name,
        roundsWon = roundsWon,
        hand = hand.map { it.toDTO() },
        board = board.toDTO(),
        hasPassed = hasPassed,
    )
}