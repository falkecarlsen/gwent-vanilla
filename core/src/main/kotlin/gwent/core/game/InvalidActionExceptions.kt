package gwent.core.game

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
    InvalidActionException("The card \"${card.type.name}\" is not in player $playerId's hand.")

class ExistingQueenException(val card: Card, val playerId: Int) :
    InvalidActionException("There can only ever be one queen on the entire battlefield.")

class MissingRowParameterException(val card: Card) : InvalidActionException("Row must specified for wild cards")

class InvalidRowParameterException(val card: Card) :
    InvalidActionException("Non-wild cards like ${card.type.name} must be played in the row matching their suit")
