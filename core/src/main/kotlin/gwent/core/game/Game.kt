package gwent.core.game

import kotlin.random.Random

const val INIT_HAND_SIZE = 10
val CARDS = Card.all().size
const val ROUNDS = 3

/**
 * This class represents a game state.
 * It can be manipulated using valid [Action]s.
 */
class Game(
    player1Name: String,
    player2Name: String,
    val deck: MutableList<Card> = newDeck(),
    var currentPlayer: Int = if (Random.nextBoolean()) 0 else 1,
) {
    val players = listOf(
        Player(0, player1Name),
        Player(1, player2Name),
    )
    var round: Int = 0

    init {
        // Immediately deal cards to both players
        players[0].hand.addAll(deck.subList(0, INIT_HAND_SIZE))
        players[1].hand.addAll(deck.subList(INIT_HAND_SIZE, 2 * INIT_HAND_SIZE))
        deck.subList(0, INIT_HAND_SIZE * 2).clear()
    }

    /**
     * Returns true of the given [Action] is valid in the current state.
     * Throws an exception if the action is not valid.
     * The action is not performed.
     */
    fun validate(action: Action): Boolean {
        // Check if game is over
        if (round == ROUNDS) throw GameOverException()
        // Check if given player's turn
        if (currentPlayer != action.player) throw OtherPlayersTurnException(action.player, currentPlayer)
        return when (action) {
            is PlayCard -> validatePlayCard(action)
            is Pass -> true
        }
    }

    /**
     * Returns true of the given [PlayCard] action is valid in the current state.
     * Throws an exception if the action is not valid.
     * The action is not performed.
     */
    private fun validatePlayCard(action: PlayCard): Boolean {
        val player = players[action.player]
        val card = Card.fromID(action.card)

        // Check if player is holding the card
        if (!player.hand.contains(card)) throw NotInHandException(card, action.player)

        return true
    }

    /**
     * Try to perform the given [Action].
     * If the [Action] is not valid, an [InvalidActionException] is thrown
     * detailing why the [action] is invalid.
     */
    fun tryPerformAction(action: Action) {
        if (validate(action)) {
            when (action) {
                is PlayCard -> performPlayCard(action)
                is Pass -> performPass(action)
            }
        } else {
            TODO("Undocumented reason for action being invalid")
        }
    }

    /**
     * Performs the given [PlayCard] action.
     * Assumes that the action has been validated by [isValid].
     */
    private fun performPlayCard(action: PlayCard) {
        val player = players[action.player]
        val card = Card.fromID(action.card)

        // Move card from hand to board and recalculate power
        player.hand.remove(card)
        player.board.add(card)
        recalculatePower()

        currentPlayer = 1 - currentPlayer
    }

    /**
     * Performs the given [Pass] action.
     * Assumes that the action has been validated by [isValid].
     */
    private fun performPass(action: Pass) {

        players[action.player].hasPassed = true
        currentPlayer = 1 - currentPlayer

        // Check if round is over
        if (players[0].hasPassed && players[1].hasPassed) {

            // Determine winner
            recalculatePower() // Probably unnecessary
            val roundWinner = currentRoundWinner()
            when (roundWinner) {
                players[0] -> players[0].wonRounds += 1
                players[1] -> players[1].wonRounds += 1
            }

            // Clean up for next round
            players[0].prepareForNewRound()
            players[1].prepareForNewRound()
            round += 1
            if (roundWinner != null) currentPlayer = roundWinner.id

            // Game is over if round == ROUNDS
        }
    }

    /**
     * Returns the game winner.
     * Assumes the game is over.
     */
    fun getWinner(): Player? {
        return when {
            players[0].wonRounds > players[1].wonRounds -> players[0]
            players[0].wonRounds < players[1].wonRounds -> players[1]
            else -> null
        }
    }

    /**
     * Returns the current round winner based on the current round state.
     * Assumes power are up-to-date.
     */
    fun currentRoundWinner(): Player? {
        return when {
            players[0].board.currentPower > players[1].board.currentPower -> players[0]
            players[0].board.currentPower < players[1].board.currentPower -> players[1]
            else -> null
        }
    }

    fun isGameOver() = round == ROUNDS

    /**
     * Iterates through all players, boards, rows, and cards to make
     * sure that all currentPower variables are up-to-date.
     */
    fun recalculatePower() {
        for (player in players) {
            val board = player.board
            board.currentPower = 0
            for ((suit, row) in board.rows) {
                row.currentPower = 0
                for (card in row.cards) {
                    row.currentPower += card.basePower
                }
                board.currentPower += row.currentPower
            }
        }
    }
}