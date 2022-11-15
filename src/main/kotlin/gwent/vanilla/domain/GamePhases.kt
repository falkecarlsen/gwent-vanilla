package gwent.vanilla.domain


sealed class Phase

class SetupPhase : Phase()

data class MulliganPhase(
        val coinFlipWinner: Boolean,
        var player1DiscardsRemaining: Int,
        var player2DiscardsRemaining: Int,
        var secondPlayerHasChosenAlignment: Boolean = false
) : Phase()

data class MindPhase(
        var player1DiscardsRemaining: Int,
        var player2DiscardsRemaining: Int
) : Phase()

data class PlayPhase(
        var player1HasPassed: Boolean = false,
        var player2HasPassed: Boolean = false,
        val currentPlayer: Player,
        val round: Int = 0
) : Phase()

data class EndPhase(
        val winner: Player?
) : Phase()