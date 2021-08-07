package models

import models.Constants.ADVANTAGE_FACTOR
import models.Constants.ADVANTAGE_FACTOR_DEFAULT
import models.Constants.TERRAIN_EFFECT_DEFAULT
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
            ADVANTAGE_FACTOR_DEFAULT

    fun getTerrainEffect(terrainType: TerrainType) =
        terrainEffectMap.getOrDefault(terrainType, TERRAIN_EFFECT_DEFAULT)
}