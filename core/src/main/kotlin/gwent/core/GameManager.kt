package gwent.core

import gwent.core.game.Game

class GameManager {
    var game: Game? = null; private set

    /**
     * Attempt to start a new [Game] with the given player names.
     * If [force] is false a new game will not be started if an unfinished game is already running.
     * If [force] is true, a new game will be started even if a game is already running.
     * @return returns true if a new game was started.
     */
    fun tryStartNewGame(player1Name: String, player2Name: String, force: Boolean): Boolean {
        val g = game
        if (!force && g != null && !g.isGameOver()) return false
        game = Game(player1Name, player2Name)
        return true
    }
}