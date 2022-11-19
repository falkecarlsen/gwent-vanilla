package gwent.vanilla

import gwent.vanilla.domain.Game
import gwent.vanilla.domain.Player

fun main() {
    println("Starting Gwent backend")

    val player1 = "La Primera"
    val player2 = "El Segundo"

    var game = Game(player1, player2)
    // TODO setup listen loop for actions from middleware/client
}
