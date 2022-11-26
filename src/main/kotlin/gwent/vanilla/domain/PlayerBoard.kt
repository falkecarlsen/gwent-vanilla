package gwent.vanilla.domain

/**
 * The [PlayerBoard] holds all cards that is part of a player's army.
 * It contains of a row for each card suit.
 */
class PlayerBoard {
    val rows: MutableMap<RowSuit, Row> = mutableMapOf(
        RowSuit.SPADES to Row(),
        RowSuit.DIAMONDS to Row(),
        RowSuit.CLUBS to Row(),
    )
    var currentPower: Int = 0

    fun add(card: Card) {
        rows[card.suit.toRowSuit()!!]!!.add(card)
    }
}