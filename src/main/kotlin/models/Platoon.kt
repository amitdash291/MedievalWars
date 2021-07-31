package models

import models.Constants.PlatoonAdvantageMap
import kotlin.math.sign

data class Platoon(
    val type: PlatoonType = PlatoonType.Militia,
    val count: Int = 0
) {
    infix fun fight(other: Platoon): BattleResult {
        val strengthDiff = (this.getAdvantageFactorOver(other) * this.count) -
                (this.getDisadvantageFactorOver(other) * other.count)
        return when (strengthDiff.sign) {
            1 -> BattleResult.Win
            0 -> BattleResult.Draw
            -1 -> BattleResult.Loss
            else -> BattleResult.NoResult
        }
    }

    fun getAdvantageFactorOver(other: Platoon): Int =
        if (PlatoonAdvantageMap[this.type]?.contains(other.type) == true)
            2
        else
            1

    fun getDisadvantageFactorOver(other: Platoon): Int =
        if (PlatoonAdvantageMap.filterValues { it.contains(this.type) }.keys.contains(other.type))
            2
        else
            1
}