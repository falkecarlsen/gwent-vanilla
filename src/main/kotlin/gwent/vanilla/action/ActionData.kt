package gwent.vanilla.action

import gwent.vanilla.domain.*

/**
 * Raw representation of [Action] used for serialization.
 */
sealed class ActionData(open val player: Int)

/**
 * Raw representation of [Mulligan] used for serialization.
 */
data class MulliganData(
        override val player: Int,
        val discardedCards: List<Int>,
        val alignment: Alignment
) : ActionData(player)

/**
 * Raw representation of [MindDiscard] used for serialization.
 */
data class MindDiscardData(
        override val player: Int,
        val discardedCard: Int
) : ActionData(player)

/**
 * Raw representation of [PlayCard] used for serialization.
 */
data class PlayCardData(
        override val player: Int,
        val card: Int,
        val row: RowSuit? = null,
        val target: Int? = null
) : ActionData(player)

/**
 * Raw representation of [Pass] used for serialization.
 */
data class PassData(override val player: Int) : ActionData(player)
