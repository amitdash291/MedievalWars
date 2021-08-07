package utils

import models.*

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

    fun serializeArtillery(armySize: Int, artilleryPosition: Int) =
        (0 until armySize).map { pos ->
            if (pos == artilleryPosition)
                Constants.Artillery.SERIALIZED_REPRESENTATION
            else
                Constants.Artillery.SERIALIZED_DEFAULT
        }.joinToString("")
}