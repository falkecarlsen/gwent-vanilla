package gwent.core

import gwent.core.game.Game

/**
 * A listener for notifications about new game starts and the game state changes.
 */
interface GameStateChangeListener {
    fun onGameStateChange(game: Game)
}
