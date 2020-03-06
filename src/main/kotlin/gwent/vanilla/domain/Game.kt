package gwent.vanilla.domain

import gwent.vanilla.action.*
import kotlin.random.Random

class Game constructor(var player1: Player, var player2: Player) : Gwent {
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
                is Mulligan -> {
                    // Each player discards two cards
                    val mulliganPhase = phase as MulliganPhase
                    for (spell in action.discardedCards) {
                        discardCardFromHand(action.player, spell)
                        if (action.player == player1) {
                            mulliganPhase.player1DiscardsRemaining--
                        } else {
                            mulliganPhase.player2DiscardsRemaining--
                        }
                    }

                    chooseAlignment(action.player, action.alignment)

                    if (mulliganPhase.secondPlayerHasChosenAlignment) {
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
                        mulliganPhase.secondPlayerHasChosenAlignment = true
                    }
                }
                is MindDiscard -> {
                    val mindPhase = phase as MindPhase

                    discardCardFromHand(action.player, action.discardedCard)
                    if (action.player == player1) {
                        mindPhase.player1DiscardsRemaining--
                    } else {
                        mindPhase.player2DiscardsRemaining--
                    }

                    if (mindPhase.player1DiscardsRemaining == 0 && mindPhase.player2DiscardsRemaining == 0) {
                        round += 1
                        phase = PlayPhase(currentPlayer = startingPlayer, round = round + 1)
                    }
                }
                is PlayCard -> TODO()
                is Pass -> {
                    val playPhase = phase as PlayPhase
                    action.player.pass()
                    if (action.player == player1) {
                        playPhase.player1HasPassed = true
                    } else {
                        playPhase.player2HasPassed = true
                    }

                    if (playPhase.player1HasPassed && playPhase.player2HasPassed) {
                        // End of round phase
                        //TODO("check for winner and clear board")
                        when {
                            player1.wonRounds > 1 -> phase = EndPhase(player1)
                            player2.wonRounds > 1 -> phase = EndPhase(player2)
                            round == 2 -> {
                                // No player has won more than 1 round and last round has been played
                                phase = EndPhase(getWinner())
                            }
                            else -> {
                                round += 1
                                val tiebreaker = if (player1.alignment == Alignment.Might && player2.alignment != Alignment.Might) {
                                    player1
                                } else if (player1.alignment != Alignment.Might && player2.alignment == Alignment.Might) {
                                    player2
                                } else {
                                    if (playPhase.currentPlayer == player1) player2 else player1
                                }

                                phase = PlayPhase(currentPlayer = decideRoundWinner() ?: tiebreaker)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getWinner(): Player? {
        return when {
            player1.wonRounds > player2.wonRounds -> player1
            player1.wonRounds < player2.wonRounds -> player2
            else -> null
        }
    }

    fun decideRoundWinner(): Player? {
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
                    // TODO picture-cards, seer-buff
                    player.board.power += cardPower * creature.empowerMultiplier
                }
            }
        }
    }

    private fun flipCoin(): Boolean {
        return Random.nextBoolean()
    }

    override fun getPlayers(): List<Player> {
        return listOf(player1, player2)
    }

    override fun chooseAlignment(player: Player, alignment: Alignment) {
        player.alignment = alignment
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

    override fun discardCardFromHand(player: Player, spell: Spell) {
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