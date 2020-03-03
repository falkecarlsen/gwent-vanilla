package gwent.vanilla.domain

import gwent.vanilla.action.Action
import kotlin.random.Random

class Game constructor(override var players: List<Player>) : Gwent {

    fun isActionValid(action: Action): Boolean {
        TODO("not implemented")
    }

    fun performAction(action: Action) {
        TODO("not implemented")
    }

    override fun getGame(): Game {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWinner(game: Game): Player? {
        val wonRoundsComparator = compareBy<Player> { it.wonRounds }
        val alignmentComparator = wonRoundsComparator.thenBy {
            when (it.alignment) {
                Alignment.Might -> 1
                else -> 0
            }
        }

        return players.maxWith(alignmentComparator)
    }

    fun playGame() {
        // Generate deck
        val deck = generateDeck()
        // Deal cards to both players
        for (i in 0..23) {
            if (i % 2 == 0) {
                TODO("not implemented")
            } else {
                TODO("not implemented")
            }
        }
        assert(deck.size == 30) { "Deal didn't go as planned" }

        val startingPlayer: Player = players[flipCoin()]

        // Each player discards two cards
        for (player in players) {
            for (i in 0..1) {
                discardCardFromHand(player)
            }
            assert(player.hand.size == 10) { "Discarding of two cards for each player didn't go as planned" }
        }

        // Choose alignments in order (starting player last for slight advantage)

        // Play three rounds or until a player has two wins
        for (i in 0..2) {
            //TODO break if winner is found (maybe check edge-cases where winning player with 2 rounds has no more cards
            // but losing player is the only Might alignment, and will always win last round, such that they win 1 round)
            var round: Round = Round(players[0], players[1], startingPlayer)
        }
    }

    fun flipCoin(): Int {
        return if (Random.nextBoolean()) 1 else 0
    }

    override fun getStartingPlayer(game: Game) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHand(player: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun playCard(player: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun chooseAlignment(player: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pass(player: Player) {
        player.pass()
    }

    override fun discardCardFromHand(player: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPlayer(): Player {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupGame(): Game {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun generateDeck(): MutableList<Spell> {
        return mutableListOf(
                Spell(SPELL_SPADES_ACE),
                Spell(SPELL_SPADES_2),
                Spell(SPELL_SPADES_3),
                Spell(SPELL_SPADES_4),
                Spell(SPELL_SPADES_5),
                Spell(SPELL_SPADES_6),
                Spell(SPELL_SPADES_7),
                Spell(SPELL_SPADES_8),
                Spell(SPELL_SPADES_9),
                Spell(SPELL_SPADES_10),
                Spell(SPELL_SPADES_JACK),
                Spell(SPELL_SPADES_QUEEN),
                Spell(SPELL_SPADES_KING),
                Spell(SPELL_CLUBS_ACE),
                Spell(SPELL_CLUBS_2),
                Spell(SPELL_CLUBS_3),
                Spell(SPELL_CLUBS_4),
                Spell(SPELL_CLUBS_5),
                Spell(SPELL_CLUBS_6),
                Spell(SPELL_CLUBS_7),
                Spell(SPELL_CLUBS_8),
                Spell(SPELL_CLUBS_9),
                Spell(SPELL_CLUBS_10),
                Spell(SPELL_CLUBS_JACK),
                Spell(SPELL_CLUBS_QUEEN),
                Spell(SPELL_CLUBS_KING),
                Spell(SPELL_DIAMONDS_ACE),
                Spell(SPELL_DIAMONDS_2),
                Spell(SPELL_DIAMONDS_3),
                Spell(SPELL_DIAMONDS_4),
                Spell(SPELL_DIAMONDS_5),
                Spell(SPELL_DIAMONDS_6),
                Spell(SPELL_DIAMONDS_7),
                Spell(SPELL_DIAMONDS_8),
                Spell(SPELL_DIAMONDS_9),
                Spell(SPELL_DIAMONDS_10),
                Spell(SPELL_DIAMONDS_JACK),
                Spell(SPELL_DIAMONDS_QUEEN),
                Spell(SPELL_DIAMONDS_KING),
                Spell(SPELL_HEARTS_ACE),
                Spell(SPELL_HEARTS_2),
                Spell(SPELL_HEARTS_3),
                Spell(SPELL_HEARTS_4),
                Spell(SPELL_HEARTS_5),
                Spell(SPELL_HEARTS_6),
                Spell(SPELL_HEARTS_7),
                Spell(SPELL_HEARTS_8),
                Spell(SPELL_HEARTS_9),
                Spell(SPELL_HEARTS_10),
                Spell(SPELL_HEARTS_JACK),
                Spell(SPELL_HEARTS_QUEEN),
                Spell(SPELL_HEARTS_KING),
                Spell(SPELL_JOKER),
                Spell(SPELL_JOKER)
        ).also { it.shuffle() }
    }
}