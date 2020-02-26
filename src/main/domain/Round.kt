package main.domain

import main.flipCoin

data class Round(val player1: Player, val player2: Player) {
    val startingPlayer: Player = if (flipCoin()) player1 else player2
    var boards: Map<Player, Board> = mapOf(Pair(player1, Board()), Pair(player2, Board()))
    var turn = 0
    lateinit var lastPlayer: Player

    fun playRound() {
        if (player1.hasPassed() and player2.hasPassed()) {
            decideWinner()
        } else if (player1.hasPassed()) {
            // let player2 play until pass
        } else if (player2.hasPassed()) {
            // let player1 play until pass
        } else {
            if (turn == 0) {
                playTurn(startingPlayer)
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
        // tally power on both sides
    }
}