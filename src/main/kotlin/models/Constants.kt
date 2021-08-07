package models

import models.PlatoonType.*
import models.TerrainType.*

object Constants {

    object Artillery {
        const val STRENGTH = 50
        const val SERIALIZED_REPRESENTATION = "A"
        const val SERIALIZED_DEFAULT = "."
    }

    const val ADVANTAGE_FACTOR = 2
    const val DEFAULT_POSITION = -1

    val PLATOON_CONFIGURATIONS: Map<PlatoonType, PlatoonConfiguration> = mapOf(
        Militia to PlatoonConfiguration(
            advantageAgainst = listOf(Spearmen, LightCavalry),
            terrainEffectMap = mapOf(
                Hill to 0.5,
                Muddy to 2.0,
            )
        ),
        Spearmen to PlatoonConfiguration(
            advantageAgainst = listOf(LightCavalry, HeavyCavalry),
            terrainEffectMap = mapOf(
                Hill to 0.5,
                Muddy to 2.0,
            )
        ),
        LightCavalry to PlatoonConfiguration(
            advantageAgainst = listOf(FootArcher, CavalryArcher),
            terrainEffectMap = mapOf(
                Hill to 0.5,
                Plains to 2.0,
            )
        ),
        HeavyCavalry to PlatoonConfiguration(
            advantageAgainst = listOf(Militia, FootArcher, LightCavalry),
            terrainEffectMap = mapOf(
                Hill to 0.5,
                Plains to 2.0,
            )
        ),
        FootArcher to PlatoonConfiguration(
            advantageAgainst = listOf(Militia, CavalryArcher),
            terrainEffectMap = mapOf(
                Hill to 2.0,
                Muddy to 2.0,
            )
        ),
        CavalryArcher to PlatoonConfiguration(
            advantageAgainst = listOf(Spearmen, HeavyCavalry),
            terrainEffectMap = mapOf(
                Hill to 2.0,
                Plains to 2.0,
            )
        ),
    )
}