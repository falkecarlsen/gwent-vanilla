package gwent.vanilla.domain

data class Round(val player1: Player, val player2: Player, val startingPlayer: Player) {
    var boards: Map<Player, Board> = mapOf(player1 to Board(), player2 to Board())
    var turn = 0
    lateinit var lastPlayer: Player
}