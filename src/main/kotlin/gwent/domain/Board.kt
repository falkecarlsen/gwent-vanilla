package gwent.domain

data class Board(var rows: List<Row> = mutableListOf(), var power: Int = 0)