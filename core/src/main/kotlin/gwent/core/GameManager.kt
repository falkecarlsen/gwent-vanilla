package gwent.core

import gwent.core.game.Action
import gwent.core.game.Game
import gwent.core.game.InvalidActionException

class GameManager {
    var game: Game? = null; private set
    private var changeListeners = mutableListOf<GameStateChangeListener>()

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
        changeListeners.forEach { it.onGameStateChange(game!!) }
        return true
    }

    /**
     * Try to perform the given [Action].
     * If the [Action] is not valid, an [InvalidActionException] is thrown
     * detailing why the [action] is invalid.
     * If the action is successfully performed, listeners will be notified.
     */
    fun tryPerformAction(action: Action): Boolean {
        val game = game ?: throw IllegalStateException("No game is running")
        val changed = game.tryPerformAction(action)
        if (changed) {
            changeListeners.forEach { it.onGameStateChange(game) }
        }
        return changed
    }

    /**
     * Register the given [GameStateChangeListener].
     * It will be notified whenever a new game starts and the game state changes.
     */
    fun registerListener(listener: GameStateChangeListener) {
        changeListeners.add(listener)
    }

    fun unregisterListener(listener: GameStateChangeListener) {
        changeListeners.remove(listener)
    }

    fun doesGameExist(): Boolean {
        return game != null
    }
}