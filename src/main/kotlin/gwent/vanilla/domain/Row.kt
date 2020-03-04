package gwent.vanilla.domain

data class Row(var cards: MutableList<Creature> = mutableListOf()) {
    fun calculateRowPower() {
        TODO("calculation of power not implemented")
    }
}
