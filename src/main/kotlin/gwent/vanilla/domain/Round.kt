package gwent.vanilla.domain

import gwent.vanilla.domain.Board
import gwent.vanilla.domain.Player

data class Round(val player1: Player, val player2: Player, val startingplayer: Player) {
    var boards: Map<Player, Board> = mapOf(player1 to Board(), player2 to Board())
    var turn = 0
    lateinit var lastPlayer: Player

    fun playRound() {
        if (player1.hasPassed() && player2.hasPassed()) {
            decideWinner()
        } else if (player1.hasPassed()) {
            // let player2 play until pass
        } else if (player2.hasPassed()) {
            // let player1 play until pass
        } else {
            if (turn == 0) {
                playTurn(this.startingplayer)
            } else {
                // Do ordinary rounds
            }
        }
    }

    fun playTurn(player: Player) {
        // Do round


        // Set player as lastPlayer
        lastPlayer = player
    }

    fun decideWinner() {
        // check that both players have passed
        // tally power on both sides, if equal check for might alignment on either player
    }
}