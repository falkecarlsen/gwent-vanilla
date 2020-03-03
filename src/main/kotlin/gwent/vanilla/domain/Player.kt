package gwent.vanilla.domain

data class Player constructor(
        val name: String,
        var wonRounds: Int = 0,
        var alignment: Alignment = Alignment.Undecided,
        var hand: MutableList<Spell> = mutableListOf()
) {
    private var pass: Boolean = false

    fun pass() {
        pass = true
    }

    fun hasPassed(): Boolean {
        return pass
    }
}