package main

import main.domain.*

fun main() {
    println("Starting Gwent backend")

    val card1 = UnitCard(3, Suit.Clubs)
    val card2 = UnitCard(3, Suit.Clubs)
    val card3 = UnitCard(3, Suit.Clubs)

    var row = Row(listOf(card1, card2, card3))

    val player1 = Player("La Primera")
    val player2 = Player("El Segundo")

    val game = Game(mapOf(player1 to Board(listOf(row), 1)))

    var deck = generateDeck()
    deck.map { println(it) }
}

fun generateDeck(): List<Card> {
    var deck: List<Card> = listOf()
    for (value in 1..12) {
        for (suit in Suit.values()) {
            when (value) {
                1, 2 -> {
                    deck += SpecialCard(value, suit)
                }
                else -> {
                    deck += UnitCard(value, suit)
                }
            }
        }
    }

    for (joker in 1..2) {
        deck += JokerCard
    }

    assert(deck.size == 54)

    return deck.shuffled()
}