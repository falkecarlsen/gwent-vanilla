package gwent.core.game

import gwent.core.game.abilities.OngoingEffect
import gwent.core.serialize.GameDTO
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
        drawCards(0, INIT_HAND_SIZE)
        drawCards(1, INIT_HAND_SIZE)
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

        // Check if player is holding the card
        if (!player.hand.contains(action.card)) throw NotInHandException(action.card, action.player)

        // Cards must be played in row matching its suit,
        // except Wild cards which can be played in any row
        if (Tag.Wild in action.card.tags && action.row == null) {
            throw MissingRowParameterException(action.card)
        } else if (Tag.Wild !in action.card.tags && action.row != null && action.row != action.card.suit.toRowSuit()) {
            throw InvalidRowParameterException(action.card)
        }

        // Only allow one queen on the board
        if (Tag.Queen in action.card.tags && queryBoard(tags = listOf(Tag.Queen)).isNotEmpty())
            throw ExistingQueenException(action.card, action.player)

        return true
    }

    /**
     * Try to perform the given [Action].
     * If the [Action] is not valid, an [InvalidActionException] is thrown
     * detailing why the [action] is invalid.
     */
    fun tryPerformAction(action: Action): Boolean {
        if (validate(action)) {
            when (action) {
                is PlayCard -> performPlayCard(action)
                is Pass -> performPass(action)
            }
            return true
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

        // Move card from hand to board and recalculate power
        val rowSuit = action.row ?: action.card.suit.toRowSuit()!!
        action.card.immediate?.apply(action.card, this)
        player.hand.remove(action.card)
        if (Tag.Spy !in action.card.tags) {
            player.board.add(action.card, rowSuit)
        } else {
            action.card.owner = 1 - player.index
            players[1 - player.index].board.add(action.card, rowSuit)
        }
        if (Tag.Unit in action.card.tags) player.lastPlayedUnit = action.card
        recalculatePower()

        // Auto-pass if empty hand
        if (player.hand.isEmpty())
            player.hasPassed = true

        // Swap to other player, unless they have passed
        if (!players[1 - currentPlayer].hasPassed)
            currentPlayer = 1 - currentPlayer

        // Round may have ended due to auto-pass
        checkIfRoundOver()
    }

    /**
     * Performs the given [Pass] action.
     * Assumes that the action has been validated by [isValid].
     */
    private fun performPass(action: Pass) {

        players[action.player].hasPassed = true
        currentPlayer = 1 - currentPlayer

        checkIfRoundOver()
    }

    /**
     * Checks if round is over and makes the game proceed to the next round if so.
     * This includes determining the winner of the round, picking the starting player of the next round,
     * as well as auto-passing and auto-ending if possible.
     */
    private fun checkIfRoundOver() {
        if (players[0].hasPassed && players[1].hasPassed) {

            // Determine winner
            recalculatePower() // Probably unnecessary
            val roundWinner = currentRoundWinner()
            when (roundWinner) {
                players[0] -> players[0].roundsWon += 1
                players[1] -> players[1].roundsWon += 1
            }

            // Clean up for next round
            players[0].resetBoardAndPassedFlag()
            players[1].resetBoardAndPassedFlag()
            round += 1

            if (roundWinner != null) currentPlayer = roundWinner.index

            recalculatePower()

            // Auto-end game if both players have empty hands
            // Otherwise, auto-pass for players with empty hand
            if (players[0].hand.isEmpty() && players[1].hand.isEmpty())
                round = ROUNDS
            else if (players[0].hand.isEmpty())
                players[0].hasPassed = true
            else if (players[1].hand.isEmpty())
                players[1].hasPassed = true

            // Swap current player in case of auto-pass
            if (players[currentPlayer].hasPassed) currentPlayer = 1 - currentPlayer

            // Game is over if round == ROUNDS
        }
    }

    /**
     * Returns the game winner.
     * Assumes the game is over.
     */
    fun getWinner(): Player? {
        return when {
            players[0].roundsWon > players[1].roundsWon -> players[0]
            players[0].roundsWon < players[1].roundsWon -> players[1]
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

        val ongoingEffects = mutableListOf<Pair<Card, OngoingEffect>>()

        // Reset cards to their base power and collect active ongoing effects
        for (player in players) {
            val board = player.board
            for ((_, row) in board.rows) {
                for (card in row.cards) {
                    card.currentPower = card.basePower
                    if (card.ongoing != null) ongoingEffects.add(Pair(card, card.ongoing))
                }
            }
        }

        // Apply ongoing effects
        for ((source, effect) in ongoingEffects.sortedBy { it.second.order() }) {
            effect.apply(source, this)
        }

        // Propagate current power to rows and board
        for (player in players) {
            val board = player.board
            board.currentPower = 0
            for ((_, row) in board.rows) {
                row.currentPower = 0
                for (card in row.cards) {
                    row.currentPower += card.currentPower
                }
                board.currentPower += row.currentPower
            }
        }
    }

    fun drawCards(playerId: Int, count: Int) {
        players[playerId].hand.addAll(deck.subList(0, count))
        deck.subList(0, count).clear()
        players[playerId].hand.forEach { it.owner = playerId }
    }

    /**
     * Query the board to get a collection of cards satisfying all the given requirements.
     */
    fun queryBoard(
        player: Int? = null,
        row: RowSuit? = null,
        suit: Suit? = null,
        tags: List<Tag>? = null,
    ): List<Card> {
        val res = mutableListOf<Card>()
        for (pl in players) {
            if (player != null && player != pl.index) continue
            for ((rs, r) in pl.board.rows) {
                if (row != null && row != rs) continue
                for (card in r.cards) {
                    if (suit != null && suit != card.suit) continue
                    if (tags != null && !card.tags.containsAll(tags)) continue
                    res.add(card)
                }
            }
        }
        return res
    }

    /**
     * Convert to data transfer object.
     */
    fun toDTO() = GameDTO(
        deck = deck.map { it.toDTO() },
        players = players.map { it.toDTO() },
        currentPlayer = currentPlayer,
        round = round,
    )
}