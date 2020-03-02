package main

import main.domain.*
import kotlin.random.Random
import kotlin.test.asserter

fun main() {
    println("Starting Gwent backend")

    val player1 = Player("La Primera")
    val player2 = Player("El Segundo")

    var game = Game(listOf(player1, player2))
    game.playGame()
}



