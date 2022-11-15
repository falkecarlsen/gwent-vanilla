package gwent.vanilla.domain

data class Row(val cards: MutableList<Creature> = mutableListOf()) {

    fun contains(creature: Creature): Boolean {
        return cards.contains(creature)
    }

    fun addCreature(creature: Creature) {
        assert(creature.row == null) { "Creature $creature is in another row already" }
        cards.add(creature)
        creature.row = this
    }

    fun removeCreature(creature: Creature) {
        assert(creature.row == this) { "Creature $creature is not in this row" }
    }
}
