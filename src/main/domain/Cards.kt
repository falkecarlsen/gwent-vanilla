package main.domain

// ============ CREATURES ==========

class Creature(val creatureType: CreatureType, var empowerMultiplier: Int = 1)

sealed class CreatureType(suit: Suit, power: (/* TODO Relevant data (Board?) */) -> Int)

object CREATURE_SPADES_3 : CreatureType(Suit.Hearts, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_SPADES_4 : CreatureType(Suit.Hearts, { 4 })
object CREATURE_SPADES_5 : CreatureType(Suit.Hearts, { 5 })
object CREATURE_SPADES_6 : CreatureType(Suit.Hearts, { 6 })
object CREATURE_SPADES_7 : CreatureType(Suit.Hearts, { 7 })
object CREATURE_SPADES_8 : CreatureType(Suit.Hearts, { 8 })
object CREATURE_SPADES_9 : CreatureType(Suit.Hearts, { 9 })
object CREATURE_SPADES_10 : CreatureType(Suit.Hearts, { 10 })
object CREATURE_SPADES_JACK : CreatureType(Suit.Hearts, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_SPADES_QUEEN : CreatureType(Suit.Hearts, { 10 })
object CREATURE_SPADES_KING : CreatureType(Suit.Hearts, { 10 })

object CREATURE_HEARTS_3 : CreatureType(Suit.Hearts, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_HEARTS_4 : CreatureType(Suit.Hearts, { 4 })
object CREATURE_HEARTS_5 : CreatureType(Suit.Hearts, { 5 })
object CREATURE_HEARTS_6 : CreatureType(Suit.Hearts, { 6 })
// etc

// ============ SPELLS ==========

class Spell(val cardType: SpellType)

enum class RowSuit { SPADES, DIAMONDS, CLUBS }

sealed class SpellType
sealed class SimpleSpell(val cast: () -> Unit) : SpellType()
sealed class CreatureSpell(val cast: (Creature) -> Unit) : SpellType()
sealed class RowSpell(val cast: (RowSuit) -> Unit) : SpellType()

object SPELL_SPADES_ACE : SimpleSpell({ /* TODO Destroy biggest spade creatures */ })
object SPELL_SPADES_2 : SimpleSpell({ /* TODO Add weather effect to spade rows */ })
object SPELL_SPADES_3 : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) })
object SPELL_SPADES_4 : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) /* TODO Gain info on enemy cards */ })
object SPELL_SPADES_5 : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) })
object SPELL_SPADES_6 : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) })
object SPELL_SPADES_7 : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) })
object SPELL_SPADES_8 : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) })
object SPELL_SPADES_9 : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) })
object SPELL_SPADES_10 : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) /* FIXME Summon for enemy instead, and draw cards */ })
object SPELL_SPADES_JACK : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) })
object SPELL_SPADES_QUEEN : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) /* TODO Make sure there are no other queens */ })
object SPELL_SPADES_KING : SimpleSpell({ summon(CREATURE_SPADES_5, RowSuit.SPADES) })
// etc
object SPELL_HEARTS_4 : RowSpell({ row -> summon(CREATURE_HEARTS_4, row) })
object SPELL_HEARTS_5 : SpellType() {
    fun cast(rowSuit: RowSuit, creature: Creature?) {
        // TODO Summon hearts 5 and kill targeted creature (if any)
    }
}
object SPELL_HEARTS_6 : RowSpell({ row -> summon(CREATURE_HEARTS_4, row) })
// etc
object SPELL_JOKER : CreatureSpell({ it.empowerMultiplier += 1 })

fun summon(creatureType: CreatureType, rowSuit: RowSuit) {
    // TODO Create new Creature and add to relevant row
}