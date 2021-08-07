package models

import models.Constants.PLATOON_CONFIGURATIONS
import models.enums.*
import kotlin.math.sign

data class Platoon(
    val type: PlatoonType = PlatoonType.Militia,
    val count: Int = 0
) {
    fun fight(opponent: Platoon, terrainType: TerrainType = TerrainType.Default): BattleResult {
        val strengthDiff = getStrength(opponent, terrainType) -
                opponent.getStrength(this, terrainType)
        return when (strengthDiff.sign) {
            1.0 -> BattleResult.Win
            0.0 -> BattleResult.Draw
            -1.0 -> BattleResult.Loss
            else -> BattleResult.NoResult
        }
    }

    fun getStrength(opponent: Platoon, terrainType: TerrainType): Double {
        val configuration = getConfiguration()
        return count *
                configuration.getAdvantageFactorOver(opponent.type) *
                configuration.getTerrainEffect(terrainType)
    }

    fun getConfiguration() = PLATOON_CONFIGURATIONS.getOrDefault(type, PlatoonConfiguration())
}