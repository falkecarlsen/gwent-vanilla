package gwent.vanilla.domain

import gwent.vanilla.action.*
import kotlin.random.Random

class Game constructor(player1Name: String, player2Name: String) {
    val player1 = Player(0, player1Name)
    val player2 = Player(1, player2Name)
    val players = listOf(player1, player2)
    var phase: Phase = SetupPhase()
    val deck: MutableList<Spell> = generateDeck()
    val coinFlip = flipCoin()
    val startingPlayer: Player = if (coinFlip) player1 else player2
    var round: Int = -1
    var weatherEffects: Map<RowSuit, Boolean> = mapOf(
            RowSuit.DIAMONDS to false,
            RowSuit.CLUBS to false,
            RowSuit.SPADES to false
    )

    init {
        // Deal cards to both players
        player1.hand.addAll(deck.subList(0, 12))
        deck.removeAll(player1.hand)
        player2.hand.addAll(deck.subList(13, 25))
        deck.removeAll(player2.hand)
        assert(deck.size == 30 && player1.hand.size == 12 && player2.hand.size == 13) { "Deal didn't go as planned" }

        phase = MulliganPhase(coinFlip, 2, 2)
    }

    fun isActionValid(action: Action): Boolean {
        TODO("not implemented")
    }

    fun performAction(action: Action) {
        if (isActionValid(action)) {
            when (action) {
                is Mulligan -> performMulligan(action)
                is MindDiscard -> performMindDiscard(action)
                is PlayCard -> performPlayCard(action)
                is Pass -> performPass(action)
            }
        }
    }

    private fun performMulligan(action: Mulligan) {
        val mulliganPhase = phase as MulliganPhase

        // Discard card(s) and update mulligan phase
        for (spell in action.discardedCards) {
            discardCardFromHand(players[action.player], spell)
            if (action.player == player1.id) {
                mulliganPhase.player1DiscardsRemaining--
            } else {
                mulliganPhase.player2DiscardsRemaining--
            }
        }

        // Choose alignment
        players[action.player].alignment = action.alignment

        if (mulliganPhase.secondPlayerHasChosenAlignment) {

            // Mulligan is done
            // If any player chose Mind as alignment, go to Mind phase, otherwise go to play phase
            phase = if (listOf(player1, player2).any { it.alignment == Alignment.Mind }) {
                MindPhase(
                        if (player1.alignment == Alignment.Mind) 1 else 0,
                        if (player2.alignment == Alignment.Mind) 1 else 0
                )
            } else {
                round = 0
                PlayPhase(currentPlayer = startingPlayer)
            }
        } else {
            // One player (the second player) is done mulliganing
            mulliganPhase.secondPlayerHasChosenAlignment = true
        }
    }

    private fun performMindDiscard(action: MindDiscard) {
        val mindPhase = phase as MindPhase

        // Discard card
        discardCardFromHand(players[action.player], action.discardedCard)
        if (action.player == player1.id) {
            mindPhase.player1DiscardsRemaining--
        } else {
            mindPhase.player2DiscardsRemaining--
        }

        // If both players are done discarding due to Mind, go to play phase
        if (mindPhase.player1DiscardsRemaining == 0 && mindPhase.player2DiscardsRemaining == 0) {
            round += 1
            phase = PlayPhase(currentPlayer = startingPlayer, round = round + 1)
        }
    }

    private fun performPlayCard(action: PlayCard) {
        TODO()
    }

    private fun performPass(action: Pass) {
        val playPhase = phase as PlayPhase

        // Mark that the player has passed
        players[action.player].pass()
        if (action.player == player1.id) {
            playPhase.player1HasPassed = true
        } else {
            playPhase.player2HasPassed = true
        }

        // If both players has passed, the round ends
        if (playPhase.player1HasPassed && playPhase.player2HasPassed) {

            // TODO("check for winner and clear board")

            when {
                // If a player has won more than two rounds they win
                player1.wonRounds > 1 -> phase = EndPhase(player1)
                player2.wonRounds > 1 -> phase = EndPhase(player2)
                // This was last round yet no one has won two rounds => we have a tie
                round == 2 -> {
                    // No player has won more than 1 round and last round has been played
                    phase = EndPhase(getWinner())
                }
                else -> {
                    // Decide who starts next round (usually the winner)
                    val tiebreaker = if (player1.alignment == Alignment.Might && player2.alignment != Alignment.Might) {
                        player1
                    } else if (player1.alignment != Alignment.Might && player2.alignment == Alignment.Might) {
                        player2
                    } else {
                        if (playPhase.currentPlayer == player1) player2 else player1
                    }

                    // Go to next round
                    round += 1
                    phase = PlayPhase(currentPlayer = decideRoundWinner() ?: tiebreaker)
                }
            }
        }
    }

    fun getWinner(): Player? {
        return when {
            player1.wonRounds > player2.wonRounds -> player1
            player1.wonRounds < player2.wonRounds -> player2
            else -> null
        }
    }

    fun decideRoundWinner(): Player? {
        // TODO Consider Might
        return when {
            player1.board.power > player2.board.power -> player1
            player1.board.power < player2.board.power -> player2
            else -> null
        }
    }

    fun calculatePower(game: Game) {

        //per player, per board, per row, per card

        for (player in listOf(player1, player2)) {
            player.board.power = 0
            for (row in player.board.rows) {
                for (creature in row.value.cards) {
                    var cardPower = creature.creatureType.power()
                    if (weatherEffects[row.key]!!) {
                        cardPower = 2
                    }
                    if (player.alignment == Alignment.Might && creature.creatureType.picture) {
                        cardPower += 2
                    }
                    // TODO Seer-buff
                    creature.currentPower = cardPower * creature.empowerMultiplier
                    player.board.power += creature.currentPower
                }
            }
        }
    }

    private fun flipCoin(): Boolean {
        return Random.nextBoolean()
    }

    fun discardCardFromHand(player: Player, spellId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun generateDeck(): MutableList<Spell> {
        return listOf(
                SPELL_SPADES_ACE,
                SPELL_SPADES_2,
                SPELL_SPADES_3,
                SPELL_SPADES_4,
                SPELL_SPADES_5,
                SPELL_SPADES_6,
                SPELL_SPADES_7,
                SPELL_SPADES_8,
                SPELL_SPADES_9,
                SPELL_SPADES_10,
                SPELL_SPADES_JACK,
                SPELL_SPADES_QUEEN,
                SPELL_SPADES_KING,
                SPELL_CLUBS_ACE,
                SPELL_CLUBS_2,
                SPELL_CLUBS_3,
                SPELL_CLUBS_4,
                SPELL_CLUBS_5,
                SPELL_CLUBS_6,
                SPELL_CLUBS_7,
                SPELL_CLUBS_8,
                SPELL_CLUBS_9,
                SPELL_CLUBS_10,
                SPELL_CLUBS_JACK,
                SPELL_CLUBS_QUEEN,
                SPELL_CLUBS_KING,
                SPELL_DIAMONDS_ACE,
                SPELL_DIAMONDS_2,
                SPELL_DIAMONDS_3,
                SPELL_DIAMONDS_4,
                SPELL_DIAMONDS_5,
                SPELL_DIAMONDS_6,
                SPELL_DIAMONDS_7,
                SPELL_DIAMONDS_8,
                SPELL_DIAMONDS_9,
                SPELL_DIAMONDS_10,
                SPELL_DIAMONDS_JACK,
                SPELL_DIAMONDS_QUEEN,
                SPELL_DIAMONDS_KING,
                SPELL_HEARTS_ACE,
                SPELL_HEARTS_2,
                SPELL_HEARTS_3,
                SPELL_HEARTS_4,
                SPELL_HEARTS_5,
                SPELL_HEARTS_6,
                SPELL_HEARTS_7,
                SPELL_HEARTS_8,
                SPELL_HEARTS_9,
                SPELL_HEARTS_10,
                SPELL_HEARTS_JACK,
                SPELL_HEARTS_QUEEN,
                SPELL_HEARTS_KING,
                SPELL_JOKER,
                SPELL_JOKER
        )
                .mapIndexed { id, type -> Spell(id, type) }
                .mapTo(mutableListOf()) { it }
                .also { it.shuffle() }
    }
}