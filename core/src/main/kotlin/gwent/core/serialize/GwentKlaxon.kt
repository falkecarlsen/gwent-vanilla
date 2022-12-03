package gwent.core.serialize

import com.beust.klaxon.FieldRenamer
import com.beust.klaxon.Klaxon

object CamelCaseJsonFieldRenamer : FieldRenamer {
    override fun toJson(fieldName: String) = FieldRenamer.camelToUnderscores(fieldName)
    override fun fromJson(fieldName: String) = FieldRenamer.underscoreToCamel(fieldName)
}

fun gwentKlaxonSetup() = Klaxon().fieldRenamer(CamelCaseJsonFieldRenamer)
