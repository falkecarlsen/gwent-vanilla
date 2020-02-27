package main.domain

data class Game constructor(var players: List<Player>) : Gwent {

    override fun getPlayerss(): List<Player> {
        return players
    }

    override fun getGame(): Game {
        return this
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPlayer(): Player {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupGame(): Game {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}