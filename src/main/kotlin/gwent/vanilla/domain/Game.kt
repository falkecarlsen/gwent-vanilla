package gwent.vanilla.domain

import gwent.vanilla.action.Action
import kotlin.random.Random

class Game constructor(var player1: Player, var player2: Player) : Gwent {

    override fun getPlayers(): List<Player> {
        return listOf(player1, player2)

    }

    fun isActionValid(action: Action): Boolean {
        TODO("not implemented")
    }

    fun performAction(action: Action) {
        TODO("not implemented")
    }

    override fun getWinner(): Player? {
        val wonRoundsComparator = compareBy<Player> { it.wonRounds }
        val alignmentComparator = wonRoundsComparator.thenBy {
            when (it.alignment) {
                Alignment.Might -> 1
                else -> 0
            }
        }

        return listOf(player1, player2).maxWith(alignmentComparator)
    }

    fun playGame() {
        // Generate deck
        val deck = generateDeck()

        // Deal cards to both players
        player1.hand.addAll(deck.subList(0, 12))
        deck.removeAll(player1.hand)
        player2.hand.addAll(deck.subList(13, 25))
        deck.removeAll(player2.hand)
        assert(deck.size == 30 && player1.hand.size == 12 && player2.hand.size == 13) { "Deal didn't go as planned" }

        val coinFlip = flipCoin()
        val startingPlayer: Player = if (coinFlip) player1 else player2

        // Each player discards two cards
        for (i in 0..1) {
            discardCardFromHand(player1)
        }
        assert(player1.hand.size == 10) { "Discarding of two cards for player 1 didn't succeed" }
        for (i in 0..1) {
            discardCardFromHand(player2)
        }
        assert(player2.hand.size == 10) { "Discarding of two cards for player 1 didn't succeed" }


        // Choose alignments in order (starting player last for slight advantage)
        if (coinFlip) {
            // Choose alignment for player 2 first
            chooseAlignment(player2, Alignment.Magic)
            chooseAlignment(player1, Alignment.Magic)
        } else {
            // Choose alignment for player 1 first
            chooseAlignment(player1, Alignment.Magic)
            chooseAlignment(player2, Alignment.Magic)
        }

        // Play three rounds or until a player has two wins
        for (i in 0..2) {
            //TODO break if winner is found (maybe check edge-cases where winning player with 2 rounds has no more cards
            // but losing player is the only Might alignment, and will always win last round, such that they win 1 round)
            val round: Round = Round(player1, player2, startingPlayer)
            round.playRound()
        }
    }

    fun flipCoin(): Boolean {
        return Random.nextBoolean()
    }

    override fun chooseAlignment(player: Player, alignment: Alignment) {
        player.alignment = alignment
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