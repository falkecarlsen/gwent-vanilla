package gwent.vanilla.domain

class Creature(val creatureType: CreatureType) {
    var row: Row? = null
    var empowerMultiplier = 1
    var currentPower = 0
}

sealed class CreatureType(val suit: Suit, val picture: Boolean, val power: (/* TODO Relevant data (Board?) */) -> Int)

object CREATURE_SPADES_3 : CreatureType(Suit.Spades, false, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_SPADES_4 : CreatureType(Suit.Spades, false, { 4 })
object CREATURE_SPADES_5 : CreatureType(Suit.Spades, false, { 5 })
object CREATURE_SPADES_6 : CreatureType(Suit.Spades, false, { 6 })
object CREATURE_SPADES_7 : CreatureType(Suit.Spades, false, { 7 })
object CREATURE_SPADES_8 : CreatureType(Suit.Spades, false, { 8 })
object CREATURE_SPADES_9 : CreatureType(Suit.Spades, false, { 9 })
object CREATURE_SPADES_10 : CreatureType(Suit.Spades, false, { 10 })
object CREATURE_SPADES_JACK : CreatureType(Suit.Spades, true, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_SPADES_QUEEN : CreatureType(Suit.Spades, true, { 10 })
object CREATURE_SPADES_KING : CreatureType(Suit.Spades, true, { 10 })

object CREATURE_CLUBS_3 : CreatureType(Suit.Clubs, false, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_CLUBS_4 : CreatureType(Suit.Clubs, false, { 4 })
object CREATURE_CLUBS_5 : CreatureType(Suit.Clubs, false, { 5 })
object CREATURE_CLUBS_6 : CreatureType(Suit.Clubs, false, { 6 })
object CREATURE_CLUBS_7 : CreatureType(Suit.Clubs, false, { 7 })
object CREATURE_CLUBS_8 : CreatureType(Suit.Clubs, false, { 8 })
object CREATURE_CLUBS_9 : CreatureType(Suit.Clubs, false, { 9 })
object CREATURE_CLUBS_10 : CreatureType(Suit.Clubs, false, { 10 })
object CREATURE_CLUBS_JACK : CreatureType(Suit.Clubs, true, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_CLUBS_QUEEN : CreatureType(Suit.Clubs, true, { 10 })
object CREATURE_CLUBS_KING : CreatureType(Suit.Clubs, true, { 10 })

object CREATURE_DIAMONDS_3 : CreatureType(Suit.Diamonds, false, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_DIAMONDS_4 : CreatureType(Suit.Diamonds, false, { 4 })
object CREATURE_DIAMONDS_5 : CreatureType(Suit.Diamonds, false, { 5 })
object CREATURE_DIAMONDS_6 : CreatureType(Suit.Diamonds, false, { 6 })
object CREATURE_DIAMONDS_7 : CreatureType(Suit.Diamonds, false, { 7 })
object CREATURE_DIAMONDS_8 : CreatureType(Suit.Diamonds, false, { 8 })
object CREATURE_DIAMONDS_9 : CreatureType(Suit.Diamonds, false, { 9 })
object CREATURE_DIAMONDS_10 : CreatureType(Suit.Diamonds, false, { 10 })
object CREATURE_DIAMONDS_JACK : CreatureType(Suit.Diamonds, true, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_DIAMONDS_QUEEN : CreatureType(Suit.Diamonds, true, { 10 })
object CREATURE_DIAMONDS_KING : CreatureType(Suit.Diamonds, true, { 10 })

object CREATURE_HEARTS_3 : CreatureType(Suit.Hearts, false, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_HEARTS_4 : CreatureType(Suit.Hearts, false, { 4 })
object CREATURE_HEARTS_5 : CreatureType(Suit.Hearts, false, { 5 })
object CREATURE_HEARTS_6 : CreatureType(Suit.Hearts, false, { 6 })
object CREATURE_HEARTS_7 : CreatureType(Suit.Hearts, false, { 7 })
object CREATURE_HEARTS_8 : CreatureType(Suit.Hearts, false, { 8 })
object CREATURE_HEARTS_9 : CreatureType(Suit.Hearts, false, { 9 })
object CREATURE_HEARTS_10 : CreatureType(Suit.Hearts, false, { 10 })
object CREATURE_HEARTS_JACK : CreatureType(Suit.Hearts, true, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_HEARTS_QUEEN : CreatureType(Suit.Hearts, true, { 10 })
object CREATURE_HEARTS_KING : CreatureType(Suit.Hearts, true, { 10 })
