package gwent.vanilla.action

import gwent.vanilla.domain.*

/**
 * An Action represents what a player can do during their turn. There can be multiple legal actions depending on the
 * state of the game.
 */
sealed class Action(val player: Player)

/**
 * A Mulligan is the initial action of the game where the player discards cards and chooses [Alignment].
 */
class Mulligan(
        player: Player,
        val discardedCards: List<Spell>,
        val alignment: Alignment
) : Action(player)

/**
 * A MindDiscard is the discard that happens if a player has the Mind [Alignment].
 */
class MindDiscard(
        player: Player,
        val discardedCard: Spell
) : Action(player)

/**
 * Playing cards is the most common action in the game. Some cards requires a target row or a target creature.
 */
class PlayCard(
        player: Player,
        val card: Spell,
        val row: RowSuit? = null,
        val target: Creature? = null
) : Action(player)

/**
 * The Pass action ends the player's participation in a round.
 */
class Pass(player: Player) : Action(player)
