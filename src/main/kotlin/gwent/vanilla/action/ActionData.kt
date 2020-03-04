package gwent.vanilla.action

import gwent.vanilla.domain.*

sealed class ActionData(val player: Int)

class MulliganData(
        player: Int,
        val discardedCards: List<Int>,
        val alignment: Alignment
) : ActionData(player)

class MindDiscardData(
        player: Int,
        val discardedCard: Int
) : ActionData(player)

class PlayCardData(
        player: Int,
        val card: Int,
        val row: RowSuit? = null,
        val target: Int? = null
) : ActionData(player)

class PassData(player: Int) : ActionData(player)
