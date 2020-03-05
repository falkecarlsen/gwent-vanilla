package gwent.vanilla.action

import gwent.vanilla.domain.*

/**
 * An Action represents what a player can do during their turn. There can be multiple legal actions depending on the
 * state of the game.
 */
sealed class Action(open val player: Int)

/**
 * A Mulligan is the initial action of the game where the player discards cards and chooses [Alignment].
 */
data class Mulligan(
        override val player: Int,
        val discardedCards: List<Int>,
        val alignment: Alignment
) : Action(player)

/**
 * A MindDiscard is the discard that happens if a player has the Mind [Alignment].
 */
data class MindDiscard(
        override val player: Int,
        val discardedCard: Int
) : Action(player)

/**
 * Playing cards is the most common action in the game. Some cards requires a target row or a target creature.
 */
data class PlayCard(
        override val player: Int,
        val card: Int,
        val row: RowSuit? = null,
        val target: Int? = null
) : Action(player)

/**
 * The Pass action ends the player's participation in a round.
 */
data class Pass(override val player: Int) : Action(player)
