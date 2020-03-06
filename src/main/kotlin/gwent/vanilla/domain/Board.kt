package gwent.vanilla.domain

data class Board(var rows: MutableMap<RowSuit, Row> = mutableMapOf(), var power: Int = 0) {

    fun addCreature(creature: Creature, rowSuit: RowSuit) {
        rows[rowSuit]!!.cards.add(creature)
    }
}