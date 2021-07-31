package models

import models.PlatoonType.*

object Constants {
    val PlatoonAdvantageMap: Map<PlatoonType, List<PlatoonType>> = mapOf(
        Militia to listOf(Spearmen, LightCavalry),
        Spearmen to listOf(LightCavalry, HeavyCavalry),
        LightCavalry to listOf(FootArcher, CavalryArcher),
        HeavyCavalry to listOf(Militia, FootArcher, LightCavalry),
        FootArcher to listOf(Militia, CavalryArcher),
        CavalryArcher to listOf(Spearmen, HeavyCavalry),
    )
}