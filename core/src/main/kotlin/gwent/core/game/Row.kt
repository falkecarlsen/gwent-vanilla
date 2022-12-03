package gwent.core.game

import gwent.core.serialize.RowDTO

class Row {
    val cards: MutableList<Card> = mutableListOf()
    var currentPower: Int = 0

    fun add(card: Card) {
        cards.add(card)
    }

    /**
     * Convert to data transfer object.
     */
    fun toDTO() = RowDTO(
        currentPower = currentPower,
        units = cards.map { it.toDTO() },
    )
}
