package models

import models.Constants.ADVANTAGE_FACTOR
import models.enums.PlatoonType
import models.enums.TerrainType

data class PlatoonConfiguration(
    val advantageAgainst: List<PlatoonType> = listOf(),
    val terrainEffectMap: Map<TerrainType, Double> = mapOf()
) {

    fun getAdvantageFactorOver(platoonType: PlatoonType): Int =
        if (advantageAgainst.contains(platoonType))
            ADVANTAGE_FACTOR
        else
            1

    fun getTerrainEffect(terrainType: TerrainType) =
        terrainEffectMap.getOrDefault(terrainType, 1.0)
}