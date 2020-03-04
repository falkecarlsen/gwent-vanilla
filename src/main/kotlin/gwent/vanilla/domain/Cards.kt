package gwent.vanilla.domain

// ============ CREATURES ==========

class Creature(val creatureType: CreatureType, var empowerMultiplier: Int = 1)

sealed class CreatureType(suit: Suit, power: (/* TODO Relevant data (Board?) */) -> Int)

object CREATURE_SPADES_3 : CreatureType(Suit.Spades, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_SPADES_4 : CreatureType(Suit.Spades, { 4 })
object CREATURE_SPADES_5 : CreatureType(Suit.Spades, { 5 })
object CREATURE_SPADES_6 : CreatureType(Suit.Spades, { 6 })
object CREATURE_SPADES_7 : CreatureType(Suit.Spades, { 7 })
object CREATURE_SPADES_8 : CreatureType(Suit.Spades, { 8 })
object CREATURE_SPADES_9 : CreatureType(Suit.Spades, { 9 })
object CREATURE_SPADES_10 : CreatureType(Suit.Spades, { 10 })
object CREATURE_SPADES_JACK : CreatureType(Suit.Spades, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_SPADES_QUEEN : CreatureType(Suit.Spades, { 10 })
object CREATURE_SPADES_KING : CreatureType(Suit.Spades, { 10 })

object CREATURE_CLUBS_3 : CreatureType(Suit.Clubs, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_CLUBS_4 : CreatureType(Suit.Clubs, { 4 })
object CREATURE_CLUBS_5 : CreatureType(Suit.Clubs, { 5 })
object CREATURE_CLUBS_6 : CreatureType(Suit.Clubs, { 6 })
object CREATURE_CLUBS_7 : CreatureType(Suit.Clubs, { 7 })
object CREATURE_CLUBS_8 : CreatureType(Suit.Clubs, { 8 })
object CREATURE_CLUBS_9 : CreatureType(Suit.Clubs, { 9 })
object CREATURE_CLUBS_10 : CreatureType(Suit.Clubs, { 10 })
object CREATURE_CLUBS_JACK : CreatureType(Suit.Clubs, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_CLUBS_QUEEN : CreatureType(Suit.Clubs, { 10 })
object CREATURE_CLUBS_KING : CreatureType(Suit.Clubs, { 10 })

object CREATURE_DIAMONDS_3 : CreatureType(Suit.Diamonds, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_DIAMONDS_4 : CreatureType(Suit.Diamonds, { 4 })
object CREATURE_DIAMONDS_5 : CreatureType(Suit.Diamonds, { 5 })
object CREATURE_DIAMONDS_6 : CreatureType(Suit.Diamonds, { 6 })
object CREATURE_DIAMONDS_7 : CreatureType(Suit.Diamonds, { 7 })
object CREATURE_DIAMONDS_8 : CreatureType(Suit.Diamonds, { 8 })
object CREATURE_DIAMONDS_9 : CreatureType(Suit.Diamonds, { 9 })
object CREATURE_DIAMONDS_10 : CreatureType(Suit.Diamonds, { 10 })
object CREATURE_DIAMONDS_JACK : CreatureType(Suit.Diamonds, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_DIAMONDS_QUEEN : CreatureType(Suit.Diamonds, { 10 })
object CREATURE_DIAMONDS_KING : CreatureType(Suit.Diamonds, { 10 })

object CREATURE_HEARTS_3 : CreatureType(Suit.Hearts, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_HEARTS_4 : CreatureType(Suit.Hearts, { 4 })
object CREATURE_HEARTS_5 : CreatureType(Suit.Hearts, { 5 })
object CREATURE_HEARTS_6 : CreatureType(Suit.Hearts, { 6 })
object CREATURE_HEARTS_7 : CreatureType(Suit.Hearts, { 7 })
object CREATURE_HEARTS_8 : CreatureType(Suit.Hearts, { 8 })
object CREATURE_HEARTS_9 : CreatureType(Suit.Hearts, { 9 })
object CREATURE_HEARTS_10 : CreatureType(Suit.Hearts, { 10 })
object CREATURE_HEARTS_JACK : CreatureType(Suit.Hearts, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_HEARTS_QUEEN : CreatureType(Suit.Hearts, { 10 })
object CREATURE_HEARTS_KING : CreatureType(Suit.Hearts, { 10 })


// =========== SPELLS ==========

class Spell(val cardType: SpellType)

enum class RowSuit { SPADES, DIAMONDS, CLUBS }

sealed class SpellType
sealed class SimpleSpell(val cast: (Map<Player, Board>, Player) -> Unit) : SpellType()
sealed class CreatureSpell(val cast: (Creature, Map<Player, Board>, Player) -> Unit) : SpellType()
sealed class RowSpell(val cast: (RowSuit, Map<Player, Board>, Player) -> Unit) : SpellType()

object SPELL_SPADES_ACE : SimpleSpell({ boards, player -> /* TODO Destroy biggest spade creatures */ })
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

object SPELL_CLUBS_ACE : SimpleSpell({ boards, player -> /* TODO Destroy biggest spade creatures */ })
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

object SPELL_DIAMONDS_ACE : SimpleSpell({ boards, player -> /* TODO Destroy biggest spade creatures */ })
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

object SPELL_HEARTS_ACE : SimpleSpell({ boards, player -> /* TODO Destroy biggest spade creatures */ })
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

object SPELL_JOKER : CreatureSpell({ creature, boards, player -> creature.empowerMultiplier += 1 })

fun summon(creatureType: CreatureType, rowSuit: RowSuit, boards: Map<Player, Board>, player: Player) {
    boards[player]!!.addCreature(Creature(creatureType), rowSuit)
}