package gwent.vanilla.domain

/**
 * A player and everything that belongs to a player.
 */
class Player(
    val id: Int,
    val name: String,
) {
    var wonRounds: Int = 0
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
}