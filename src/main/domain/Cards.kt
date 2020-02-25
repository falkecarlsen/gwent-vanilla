package main.domain

sealed class Card

/**
 * FIXME: power-value modelling
 * @param value the value of a card as the numeric range of 0 = ace and 12 = king
 * @param power the power value of the given card
 */
data class UnitCard(val value: Int, val suit: Suit, val power: Int = 0) : Card()

data class SpecialCard(val value: Int, val suit: Suit) : Card()

object JokerCard : Card()