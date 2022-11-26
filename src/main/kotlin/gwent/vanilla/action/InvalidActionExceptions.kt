package gwent.vanilla.action

import gwent.vanilla.domain.Card

/**
 * An exception which is thrown when an invalid [Action] is attempted in a [Game].
 * The exception explains the reason why the action was invalid.
 * Inheriting exceptions contains further details.
 */
open class InvalidActionException(val reason: String) : RuntimeException("Invalid action: $reason")

class GameOverException() : InvalidActionException("The game has ended.")

class OtherPlayersTurnException(val givenPlayerId: Int, val currentPlayerId: Int) :
    InvalidActionException("It is not player $givenPlayerId's turn. It is $currentPlayerId's turn.")

class NotInHandException(val card: Card, val playerId: Int) :
    InvalidActionException("The card \"${card.id}\" is not in the player $playerId's hand.")
