package utils

import models.Constants
import models.Constants.PARSING_DELIMITER
import models.Platoon
import models.enums.PlatoonType
import models.enums.TerrainType

object Parser {

    fun getPlatoons(army: String): List<Platoon> =
        army.splitInputByDelimiter()
            .mapNotNull { platoon -> getPlatoon(platoon) }

    fun getTerrainTypes(terrainTypes: String): List<TerrainType> {
        return terrainTypes.splitInputByDelimiter()
            .map { terrain ->
                try {
                    TerrainType.valueOf(terrain)
                } catch (_: Exception) {
                    TerrainType.Default
                }
            }
    }

    fun serializePlatoons(army: List<Platoon>): String = army
        .joinToString(separator = PARSING_DELIMITER) { p -> "${p.type}#${p.count}" }

    fun serializeArtillery(armySize: Int, artilleryPosition: Int) =
        (0 until armySize).joinToString("") { pos ->
            if (pos == artilleryPosition)
                Constants.Artillery.SERIALIZED_REPRESENTATION
            else
                Constants.Artillery.SERIALIZED_DEFAULT
        }

    private fun String.splitInputByDelimiter() = this.split(PARSING_DELIMITER)
        .filter { it.isNotBlank() }

    private fun getPlatoon(platoon: String): Platoon? {
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
}