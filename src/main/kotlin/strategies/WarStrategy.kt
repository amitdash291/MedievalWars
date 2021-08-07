package strategies

import models.Constants.Artillery
import models.Platoon
import models.WarResult
import models.enums.BattleResult
import models.enums.TerrainType

object WarStrategy {
    fun getWarResult(
        firstArmy: List<Platoon>,
        secondArmy: List<Platoon>,
        terrainTypes: List<TerrainType>,
        firstArmyPositions: List<Int>
    ): WarResult {
        var winCount = 0
        var resultWithArtillery: WarResult = WarResult.Loss

        for (currentPosition in firstArmyPositions.indices) {
            val firstArmyPlatoonPosition = firstArmyPositions[currentPosition]
            val firstArmyPlatoon = firstArmy[firstArmyPlatoonPosition]
            val secondArmyPlatoon = secondArmy[currentPosition]
            val terrainType = terrainTypes[currentPosition]

            when (firstArmyPlatoon.fight(secondArmyPlatoon, terrainType)) {
                BattleResult.Win -> {
                    winCount++
                    if (winCount > firstArmy.size / 2)
                        return WarResult.Victory
                }
                else -> {
                    if (resultWithArtillery is WarResult.Loss &&
                        canWinWithArtillery(firstArmyPlatoon, secondArmyPlatoon, terrainType)
                    ) resultWithArtillery = WarResult.VictoryWithArtillery(currentPosition)
                }
            }
        }

        return if (resultWithArtillery is WarResult.VictoryWithArtillery && winCount == firstArmy.size / 2)
            resultWithArtillery
        else
            WarResult.Loss
    }

    private fun canWinWithArtillery(
        firstPlatoon: Platoon,
        secondPlatoon: Platoon,
        terrainType: TerrainType
    ): Boolean {
        val reducedSecondPlatoonCount = secondPlatoon.count - Artillery.STRENGTH
        if (reducedSecondPlatoonCount <= 0) return true

        val fightWithArtilleryResult = firstPlatoon.fight(
            Platoon(
                type = secondPlatoon.type,
                count = reducedSecondPlatoonCount
            ),
            terrainType
        )

        return fightWithArtilleryResult == BattleResult.Win
    }
}
