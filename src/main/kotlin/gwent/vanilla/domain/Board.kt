package gwent.vanilla.domain

data class Board(var rows: MutableMap<RowSuit, Row> = mutableMapOf(), var power: Int = 0) {
    fun calculateBoardPower() {
        rows.values.forEach { it.calculateRowPower() }
    }

    fun addCreature(creature: Creature, rowSuit: RowSuit) {
        rows[rowSuit]!!.cards.add(creature)
    }
}