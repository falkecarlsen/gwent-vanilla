package gwent.core.game.abilities

import gwent.core.game.Card
import gwent.core.game.Game
import gwent.core.game.Tag

/**
 * [OngoingEffect]s give benefits continuously while their source is in play.
  * Their application order is defined by [OngoingEffectOrdering].
 */
interface OngoingEffect {
    fun order(): OngoingEffectOrdering
    fun apply(source: Card, game: Game)
}

/**
 * Defines the order in which [OngoingEffect]s are applied.
 */
enum class OngoingEffectOrdering {
    Militia,
    Captain,
}

/**
 * The [MilitiaOngoingEffect] gives the source card more power based on the number of allies with [Tag.Militia].
 */
class MilitiaOngoingEffect(val multiplier: Int = 4) : OngoingEffect {

    override fun order() = OngoingEffectOrdering.Militia

    override fun apply(source: Card, game: Game) {
        val count = game.queryBoard(player = source.owner!!, tags = listOf(Tag.Militia)).count()
        source.currentPower += multiplier * count
    }
}

/**
 * The [CaptainOngoingEffect] gives the [source] card more power based on the number of units in its row.
 */
class CaptainOngoingEffect(val multiplier: Int = 3) : OngoingEffect {

    override fun order() = OngoingEffectOrdering.Captain

    override fun apply(source: Card, game: Game) {
        val count = game.queryBoard(player = source.owner!!, row = source.row).count()
        source.currentPower += multiplier * count
    }
}
