package main.domain

data class Player constructor(
    val name: String,
    var wonRounds: Int = 0,
    var alignment: Alignment = Alignment.Undecided
) {
    private var hand: MutableList<Card> = mutableListOf()
    private var pass: Boolean = false

    fun addCard(card: Card) {
        hand.add(card)
    }

    fun removeCard(card: Card) {
        if (card in hand) {
            hand.remove(card)
        }
    }

    fun getCards(): List<Card> {
        return hand
    }

    fun pass() {
        pass = true
    }

    fun hasPassed(): Boolean {
        return pass
    }
}