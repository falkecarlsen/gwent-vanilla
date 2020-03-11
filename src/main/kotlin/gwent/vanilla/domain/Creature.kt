package gwent.vanilla.domain

class Creature(val creatureType: CreatureType) {
    var row: Row? = null
    var empowerMultiplier = 1
    var currentPower = 0
}

sealed class CreatureType(val id: Int, val suit: Suit, val picture: Boolean, val power: (/* TODO Relevant data (Board?) */) -> Int)

object CREATURE_SPADES_3 : CreatureType(2, Suit.Spades, false, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_SPADES_4 : CreatureType(3, Suit.Spades, false, { 4 })
object CREATURE_SPADES_5 : CreatureType(4, Suit.Spades, false, { 5 })
object CREATURE_SPADES_6 : CreatureType(5, Suit.Spades, false, { 6 })
object CREATURE_SPADES_7 : CreatureType(6, Suit.Spades, false, { 7 })
object CREATURE_SPADES_8 : CreatureType(7, Suit.Spades, false, { 8 })
object CREATURE_SPADES_9 : CreatureType(8, Suit.Spades, false, { 9 })
object CREATURE_SPADES_10 : CreatureType(9, Suit.Spades, false, { 10 })
object CREATURE_SPADES_JACK : CreatureType(10, Suit.Spades, true, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_SPADES_QUEEN : CreatureType(11, Suit.Spades, true, { 10 })
object CREATURE_SPADES_KING : CreatureType(12, Suit.Spades, true, { 10 })

object CREATURE_CLUBS_3 : CreatureType(15, Suit.Clubs, false, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_CLUBS_4 : CreatureType(16, Suit.Clubs, false, { 4 })
object CREATURE_CLUBS_5 : CreatureType(17, Suit.Clubs, false, { 5 })
object CREATURE_CLUBS_6 : CreatureType(18, Suit.Clubs, false, { 6 })
object CREATURE_CLUBS_7 : CreatureType(19, Suit.Clubs, false, { 7 })
object CREATURE_CLUBS_8 : CreatureType(20, Suit.Clubs, false, { 8 })
object CREATURE_CLUBS_9 : CreatureType(21, Suit.Clubs, false, { 9 })
object CREATURE_CLUBS_10 : CreatureType(22, Suit.Clubs, false, { 10 })
object CREATURE_CLUBS_JACK : CreatureType(23, Suit.Clubs, true, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_CLUBS_QUEEN : CreatureType(24, Suit.Clubs, true, { 10 })
object CREATURE_CLUBS_KING : CreatureType(25, Suit.Clubs, true, { 10 })

object CREATURE_DIAMONDS_3 : CreatureType(28, Suit.Diamonds, false, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_DIAMONDS_4 : CreatureType(29, Suit.Diamonds, false, { 4 })
object CREATURE_DIAMONDS_5 : CreatureType(30, Suit.Diamonds, false, { 5 })
object CREATURE_DIAMONDS_6 : CreatureType(31, Suit.Diamonds, false, { 6 })
object CREATURE_DIAMONDS_7 : CreatureType(32, Suit.Diamonds, false, { 7 })
object CREATURE_DIAMONDS_8 : CreatureType(33, Suit.Diamonds, false, { 8 })
object CREATURE_DIAMONDS_9 : CreatureType(34, Suit.Diamonds, false, { 9 })
object CREATURE_DIAMONDS_10 : CreatureType(35, Suit.Diamonds, false, { 10 })
object CREATURE_DIAMONDS_JACK : CreatureType(36, Suit.Diamonds, true, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_DIAMONDS_QUEEN : CreatureType(37, Suit.Diamonds, true, { 10 })
object CREATURE_DIAMONDS_KING : CreatureType(38, Suit.Diamonds, true, { 10 })

object CREATURE_HEARTS_3 : CreatureType(41, Suit.Hearts, false, { 3 /* TODO Multiply with number of 3s */ })
object CREATURE_HEARTS_4 : CreatureType(42, Suit.Hearts, false, { 4 })
object CREATURE_HEARTS_5 : CreatureType(43, Suit.Hearts, false, { 5 })
object CREATURE_HEARTS_6 : CreatureType(44, Suit.Hearts, false, { 6 })
object CREATURE_HEARTS_7 : CreatureType(45, Suit.Hearts, false, { 7 })
object CREATURE_HEARTS_8 : CreatureType(46, Suit.Hearts, false, { 8 })
object CREATURE_HEARTS_9 : CreatureType(47, Suit.Hearts, false, { 9 })
object CREATURE_HEARTS_10 : CreatureType(48, Suit.Hearts, false, { 10 })
object CREATURE_HEARTS_JACK : CreatureType(49, Suit.Hearts, true, { 3 /* TODO Multiply with number of Creatures in the row */ })
object CREATURE_HEARTS_QUEEN : CreatureType(50, Suit.Hearts, true, { 10 })
object CREATURE_HEARTS_KING : CreatureType(51, Suit.Hearts, true, { 10 })
