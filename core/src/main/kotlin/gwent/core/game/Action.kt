package gwent.core.game

/**
 * An Action represents what a player can do during their turn.
 * There can be multiple legal actions depending on the state of the game.
 */
sealed class Action(
    /**
     * The index of the player who is taking this action.
     */
    val player: Int
)

/**
 * Playing cards is the most common action in the game.
 */
class PlayCard(
    player: Int,
    val card: Card,
    val row: RowSuit?,
) : Action(player)

/**
 * The Pass action ends the player's participation in a round.
 */
class Pass(player: Int) : Action(player)
