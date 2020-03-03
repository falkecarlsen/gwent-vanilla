package gwent.vanilla.domain

data class Round(val player1: Player, val player2: Player, val startingplayer: Player) {
    var boards: Map<Player, Board> = mapOf(player1 to Board(), player2 to Board())
    var turn = 0
    lateinit var lastPlayer: Player

    fun playRound() {
        if (player1.hasPassed() and player2.hasPassed()) {
            boards.forEach { (_, board) -> board.calcPower() }
            decideWinner()
        } else if (player1.hasPassed()) {
            // let player2 play until pass
            while (!player2.hasPassed()) {
                playTurn(player2)
            }
        } else if (player2.hasPassed()) {
            // let player1 play until pass
            while (!player1.hasPassed()) {
                playTurn(player1)
            }
        } else {
            if (turn == 0) {
                playTurn(this.startingplayer)
            } else {
                // Do ordinary rounds
                if (lastPlayer == player1) {
                    playTurn(player2)
                } else {
                    playTurn(player1)
                }
            }
        }

        turn += 1
    }

    fun playTurn(player: Player) {
        // Do round


        // Calculate entire boards power after each card has mutated round
        boards.forEach { (_, board) -> board.calcPower() }

        // Set player as lastPlayer
        lastPlayer = player
    }

    fun decideWinner(): Player {
        // tally power on both sides, if equal check for might alignment on either player

        val totalPowerComparator = compareBy<Map.Entry<Player, Board>> { it.value.power }.thenBy {
            when (it.key.alignment) {
                Alignment.Might -> 1
                else -> 0
            }
        }

        return boards.maxWith(totalPowerComparator)!!.key

    }
}