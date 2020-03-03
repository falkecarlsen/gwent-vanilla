package gwent.vanilla

import gwent.vanilla.domain.Game
import gwent.vanilla.domain.Player

fun main() {
    println("Starting Gwent backend")

    val player1 = Player("La Primera")
    val player2 = Player("El Segundo")

    var game = Game(listOf(player1, player2))
    game.playGame()
}



