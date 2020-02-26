package main

import main.domain.*
import java.lang.Math.floor
import kotlin.random.Random
import kotlin.test.asserter

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
    //deck.map { println(it) }

    playGame(player1, player2)
}

fun playGame(player1: Player, player2: Player) {
    // Generate deck
    val deck = generateDeck()
    // Deal cards to both players
    for (i in 0..23) {
        if (i % 2 == 0) {
            player1.addCard(deck.removeAt(i))
        } else {
            player2.addCard(deck.removeAt(i))
        }
    }
    asserter.assertTrue("Deal didn't go as planned", deck.size == 30)

    // Each player discards two cards
    for (i in 0..1) {
        discardCardFromHand(player1, player1.getCards().last())
    }

    for (i in 0..1) {
        discardCardFromHand(player2, player2.getCards().last())
    }

    asserter.assertTrue(
        "Discarding of two cards for each player didn't go as planned",
        player1.getCards().size == 10
    )
    asserter.assertTrue(
        "Discarding of two cards for each player didn't go as planned",
        player2.getCards().size == 10
    )


    // Choose alignments in order (starting player last for slight advantage)

    // Play three rounds or until a player has two wins
}

fun flipCoin(): Boolean {
    return Random.nextBoolean()
}

fun discardCardFromHand(player: Player, card: Card) {
    player.removeCard(card)
}

fun generateDeck(): MutableList<Card> {
    val deck: MutableList<Card> = mutableListOf()
    for (value in 0..12) {
        for (suit in Suit.values()) {
            when (value) {
                0, 1 -> {
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

    asserter.assertTrue("Generating deck failed", deck.size == 54)

    deck.shuffle()
    return deck
}