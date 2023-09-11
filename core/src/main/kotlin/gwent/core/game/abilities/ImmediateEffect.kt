package gwent.core.game.abilities

import gwent.core.game.Card
import gwent.core.game.Game

/**
 * [ImmediateEffect]s affects the battlefield immediately, when the card is played,
 * before it is added to the discard pile or the battlefield.
 */
interface ImmediateEffect {
    fun apply(source: Card, game: Game)
}

/**
 * The [DrawCardsImmediateEffect] makes the owner draw a number of cards.
 */
class DrawCardsImmediateEffect(val count: Int = 1) : ImmediateEffect {

    override fun apply(source: Card, game: Game) {
        game.drawCards(source.owner!!, count)
    }
}