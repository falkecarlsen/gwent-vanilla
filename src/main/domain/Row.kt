package main.domain

class Row(val new_cards: List<Card>) {
    var cards: List<Card> = new_cards

    init {
        for (card in cards) {
            //println(card)
        }
    }

    fun calculate_power() {
        for (card in cards) {
            when (card) {
                is UnitCard -> {
                    // Do value card calculation
                }
                is SpecialCard -> {
                    // Do special card calculation
                }
            }

        }
    }
}