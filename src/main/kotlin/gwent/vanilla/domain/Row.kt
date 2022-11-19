package gwent.vanilla.domain

class Row {
    val cards: MutableList<Card> = mutableListOf()
    var currentPower: Int = 0

    fun add(card: Card) {
        cards.add(card)
    }
}
