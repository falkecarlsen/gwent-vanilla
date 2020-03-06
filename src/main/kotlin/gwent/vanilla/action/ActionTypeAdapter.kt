package gwent.vanilla.action

import com.beust.klaxon.*
import kotlin.reflect.KClass

/**
 * TypeAdapter for handling the polymorphism of [Action]s.
 * See [Klaxon polymorphism](https://github.com/cbeust/klaxon#polymorphism)
 */
class ActionTypeAdapter : TypeAdapter<Action> {
    override fun classFor(type: Any): KClass<out Action> = when (type as String) {
        "Mulligan" -> Mulligan::class
        "MindDiscard" -> MindDiscard::class
        "PlayCard" -> PlayCard::class
        "Pass" -> Pass::class
        else -> throw KlaxonException("Unknown action type: $type")
    }
}