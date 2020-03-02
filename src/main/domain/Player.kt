package main.domain

data class Player constructor(
    val name: String,
    var wonRounds: Int = 0,
    var alignment: Alignment = Alignment.Undecided,
    var hand: List<Spell> = mutableListOf()
) {
    private var pass: Boolean = false

    fun pass() {
        pass = true
    }

    fun hasPassed(): Boolean {
        return pass
    }
}