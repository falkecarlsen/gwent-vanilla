package gwent.core.game

import gwent.core.serialize.PlayerBoardDTO

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
        val rowSuit = card.suit.toRowSuit()!!
        rows[rowSuit]!!.add(card)
        card.row = rowSuit
    }

    /**
     * Convert to data transfer object.
     */
    fun toDTO() = PlayerBoardDTO(
        currentPower = currentPower,
        spades = rows[RowSuit.SPADES]!!.toDTO(),
        clubs = rows[RowSuit.CLUBS]!!.toDTO(),
        diamonds = rows[RowSuit.DIAMONDS]!!.toDTO(),
    )
}