package gwent.vanilla.domain

data class Player constructor(val name: String) {
    var wonRounds: Int = 0
    var hand: MutableList<Spell> = mutableListOf()
    var alignment: Alignment = Alignment.Undecided
    val board: Board = Board()
    private var pass: Boolean = false

    fun pass() {
        pass = true
    }

    fun hasPassed(): Boolean {
        return pass
    }
}