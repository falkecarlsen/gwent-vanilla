package gwent.vanilla.domain

class Spell(val cardType: SpellType)

enum class RowSuit { SPADES, DIAMONDS, CLUBS }

sealed class SpellType
sealed class SimpleSpell(val cast: (Map<Player, Board>, Player) -> Unit) : SpellType()
sealed class CreatureSpell(val cast: (Creature, Map<Player, Board>, Player) -> Unit) : SpellType()
sealed class RowSpell(val cast: (RowSuit, Map<Player, Board>, Player) -> Unit) : SpellType()

object SPELL_SPADES_ACE : SimpleSpell({ boards, player -> scorch(boards, RowSuit.SPADES) })
object SPELL_SPADES_2 : SimpleSpell({ boards, player -> /* TODO Add weather effect to spade rows */ })
object SPELL_SPADES_3 : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) })
object SPELL_SPADES_4 : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) /* TODO Gain info on enemy cards */ })
object SPELL_SPADES_5 : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) })
object SPELL_SPADES_6 : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) })
object SPELL_SPADES_7 : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) })
object SPELL_SPADES_8 : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) })
object SPELL_SPADES_9 : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) })
object SPELL_SPADES_10 : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) /* FIXME Summon for enemy instead, and draw cards */ })
object SPELL_SPADES_JACK : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) })
object SPELL_SPADES_QUEEN : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) /* TODO Make sure there are no other queens */ })
object SPELL_SPADES_KING : SimpleSpell({ boards, player -> summon(CREATURE_SPADES_5, RowSuit.SPADES, boards, player) })

object SPELL_CLUBS_ACE : SimpleSpell({ boards, player -> scorch(boards, RowSuit.CLUBS) })
object SPELL_CLUBS_2 : SimpleSpell({ boards, player -> /* TODO Add weather effect to spade rows */ })
object SPELL_CLUBS_3 : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) })
object SPELL_CLUBS_4 : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) /* TODO Gain info on enemy cards */ })
object SPELL_CLUBS_5 : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) })
object SPELL_CLUBS_6 : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) })
object SPELL_CLUBS_7 : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) })
object SPELL_CLUBS_8 : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) })
object SPELL_CLUBS_9 : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) })
object SPELL_CLUBS_10 : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) /* FIXME Summon for enemy instead, and draw cards */ })
object SPELL_CLUBS_JACK : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) })
object SPELL_CLUBS_QUEEN : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) /* TODO Make sure there are no other queens */ })
object SPELL_CLUBS_KING : SimpleSpell({ boards, player -> summon(CREATURE_CLUBS_5, RowSuit.CLUBS, boards, player) })

object SPELL_DIAMONDS_ACE : SimpleSpell({ boards, player -> scorch(boards, RowSuit.DIAMONDS) })
object SPELL_DIAMONDS_2 : SimpleSpell({ boards, player -> /* TODO Add weather effect to spade rows */ })
object SPELL_DIAMONDS_3 : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) })
object SPELL_DIAMONDS_4 : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) /* TODO Gain info on enemy cards */ })
object SPELL_DIAMONDS_5 : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) })
object SPELL_DIAMONDS_6 : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) })
object SPELL_DIAMONDS_7 : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) })
object SPELL_DIAMONDS_8 : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) })
object SPELL_DIAMONDS_9 : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) })
object SPELL_DIAMONDS_10 : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) /* FIXME Summon for enemy instead, and draw cards */ })
object SPELL_DIAMONDS_JACK : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) })
object SPELL_DIAMONDS_QUEEN : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) /* TODO Make sure there are no other queens */ })
object SPELL_DIAMONDS_KING : SimpleSpell({ boards, player -> summon(CREATURE_DIAMONDS_5, RowSuit.DIAMONDS, boards, player) })

object SPELL_HEARTS_ACE : SimpleSpell({ boards, player -> scorch(boards, null) })
object SPELL_HEARTS_2 : SimpleSpell({ boards, player -> /* TODO Add weather effect to spade rows */ })
object SPELL_HEARTS_3 : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) })
object SPELL_HEARTS_4 : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) /* TODO Gain info on enemy cards */ })
object SPELL_HEARTS_5 : SpellType() {
    fun cast(rowSuit: RowSuit, creature: Creature?) {
        // TODO Summon hearts 5 and kill targeted creature (if any)
    }
}

object SPELL_HEARTS_6 : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) })
object SPELL_HEARTS_7 : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) })
object SPELL_HEARTS_8 : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) })
object SPELL_HEARTS_9 : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) })
object SPELL_HEARTS_10 : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) /* FIXME Summon for enemy instead, and draw cards */ })
object SPELL_HEARTS_JACK : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) })
object SPELL_HEARTS_QUEEN : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) /* TODO Make sure there are no other queens */ })
object SPELL_HEARTS_KING : RowSpell({ row, boards, player -> summon(CREATURE_HEARTS_5, row, boards, player) })

object SPELL_JOKER : CreatureSpell({ creature, _, _ -> creature.empowerMultiplier += 1 })

/**
 * Summons a creature of the given type in the given row of the given player.
 */
fun summon(creatureType: CreatureType, rowSuit: RowSuit, boards: Map<Player, Board>, player: Player) {
    boards[player]!!.addCreature(Creature(creatureType), rowSuit)
}

/**
 * Destroys the creature(s) with the greatest power in the given row suit. If no row is given
 * all rows are affected. Kings are immune and therefore ignored.
 */
fun scorch(boards: Map<Player, Board>, rowFilter: RowSuit?) {
    val immuneCreatureTypes = listOf(CREATURE_CLUBS_KING, CREATURE_DIAMONDS_KING, CREATURE_SPADES_KING, CREATURE_HEARTS_KING)

    // Find all creatures to consider
    val candidates = boards.values.flatMap {
        if (rowFilter == null) it.rows.values.flatMap { it.cards }
        else it.rows[rowFilter]!!.cards
    }.filter { it.creatureType !in immuneCreatureTypes}

    // Remove the creature(s) with the greatest power
    val greatestPower = candidates.map { it.currentPower }.max()
    if (greatestPower != null) {
        val creaturesToRemove = candidates.filter { it.currentPower == greatestPower }
        for (creature in creaturesToRemove) {
            creature.row!!.removeCreature(creature)
        }
    }
}