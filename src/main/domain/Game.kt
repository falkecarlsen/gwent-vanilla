package main.domain

class Game constructor(var playerz: List<Player>) : Gwent {
    override fun getPlayers(): List<Player> {
        return playerz
    }

    override fun getGame(): Game {
        return this
    }

    override fun getWinner(game: Game) {
        val winner = playerz.maxWith(object : Comparator<Player> {
            override fun compare(o1: Player, o2: Player): Int = when {
                o1.wonRounds > o2.wonRounds -> 1
                o1.wonRounds == o2.wonRounds -> 0 if (o1)

            }{
            }
        })
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