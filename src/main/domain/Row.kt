package main.domain

data class Row(var cards: List<Spell> = mutableListOf()) {
    fun calculate_power() {
        TODO("calculation of power not implemented")
    }
}
