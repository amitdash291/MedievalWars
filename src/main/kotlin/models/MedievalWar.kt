package models

import models.Constants.Artillery
import models.enums.TerrainType
import models.exceptions.NoWinningPlanException
import strategies.WarStrategy
import utils.MathsUtil

data class MedievalWar(
    val firstArmy: List<Platoon>,
    val secondArmy: List<Platoon>,
    val terrainTypes: List<TerrainType>
) {

    fun planBattle(): BattlePlan {
        val permutatedPositionsList = MathsUtil.generatePermutations(firstArmy.indices)
        var battlePlanWithArtillery: BattlePlan? = null

        for (positions in permutatedPositionsList) {
            when (val warResult = WarStrategy.getWarResult(firstArmy, secondArmy, terrainTypes, positions)) {
                is WarResult.Victory -> {
                    return BattlePlan(
                        battleOrder = positions.map { p -> firstArmy[p] },
                        artilleryPosition = Artillery.DEFAULT_POSITION
                    )
                }
                is WarResult.VictoryWithArtillery -> {
                    battlePlanWithArtillery = battlePlanWithArtillery ?: BattlePlan(
                        battleOrder = positions.map { p -> firstArmy[p] },
                        artilleryPosition = warResult.artilleryPosition
                    )
                }
                else -> {
                    continue
                }
            }
        }

        return battlePlanWithArtillery ?: throw NoWinningPlanException("There is no chance of winning")
    }
}
