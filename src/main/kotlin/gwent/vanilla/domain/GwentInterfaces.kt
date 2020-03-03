package gwent.vanilla.domain

interface GwentView {
    fun getPlayers(): List<Player>
    fun getWinner(): Player?
    fun getStartingPlayer(game: Game)
}

interface GwentInteract {
    fun getHand(player: Player)
    fun playCard(player: Player)
    fun chooseAlignment(player: Player, alignment: Alignment)
    fun pass(player: Player)
    fun discardCardFromHand(player: Player)
}

interface GwentSetup {
    fun createPlayer(): Player
    fun setupGame(): Game
}

interface Gwent : GwentView, GwentInteract, GwentSetup