package gwent.domain

import kotlin.random.Random

class Game constructor(override var players: List<Player>) : Gwent {

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
        TODO("not implemented")
    }
}