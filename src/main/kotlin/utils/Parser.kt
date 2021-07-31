package utils

import models.Platoon
import models.PlatoonType

object Parser {
    fun getPlatoons(army: String): List<Platoon> =
        army.split(";")
            .filter { it.isNotBlank() }
            .mapNotNull { platoon -> getPlatoon(platoon) }

    fun getPlatoon(platoon: String): Platoon? {
        val regex = Regex("""(\w+)#(\d+)""")
        return regex.matchEntire(platoon)
            ?.destructured
            ?.let { (type, count) ->
                val platoonType = try {
                    PlatoonType.valueOf(type)
                } catch (_: Exception) {
                    return null
                }
                Platoon(platoonType, count.toInt())
            }
    }

    fun serializePlatoons(army: List<Platoon>): String = army
        .joinToString(separator = ";") { p -> "${p.type}#${p.count}" }
}