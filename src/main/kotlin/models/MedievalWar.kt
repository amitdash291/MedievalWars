package models

import models.Constants.Artillery
import models.enums.BattleResult
import models.enums.TerrainType
import models.exceptions.NoWinningPlanException
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
            var winCount = 0
            var artilleryPosition = Artillery.DEFAULT_POSITION

            for (currentPosition in positions.indices) {
                val platoon = firstArmy[positions[currentPosition]]
                val opponentPlatoon = secondArmy[currentPosition]
                val terrainType = terrainTypes[currentPosition]
                val originalResult = platoon.fight(opponentPlatoon, terrainType)

                if (originalResult == BattleResult.Win) {
                    winCount++
                } else if (artilleryPosition == Artillery.DEFAULT_POSITION &&
                    canWinBattleWithArtillery(platoon, opponentPlatoon, terrainType)
                ) {
                    artilleryPosition = currentPosition
                }

                if (isBattleWon(winCount)) {
                    return BattlePlan(
                        battleOrder = positions.map { p -> firstArmy[p] },
                        artilleryPosition = Artillery.DEFAULT_POSITION
                    )
                }
            }

            if (isBattleWonWithArtillery(artilleryPosition, winCount)) {
                battlePlanWithArtillery = battlePlanWithArtillery ?: BattlePlan(
                    battleOrder = positions.map { p -> firstArmy[p] },
                    artilleryPosition = artilleryPosition
                )
            }
        }

        return battlePlanWithArtillery ?: throw NoWinningPlanException("There is no chance of winning")
    }

    private fun isBattleWon(winCount: Int) = winCount > firstArmy.size / 2

    private fun isBattleWonWithArtillery(artilleryPosition: Int, winCount: Int) =
        artilleryPosition > Artillery.DEFAULT_POSITION &&
                winCount == firstArmy.size / 2

    private fun canWinBattleWithArtillery(
        platoon: Platoon,
        opponentPlatoon: Platoon,
        terrainType: TerrainType
    ): Boolean {
        val reducedOpponentCount = opponentPlatoon.count - Artillery.STRENGTH
        if (reducedOpponentCount <= 0) return true

        val fightWithArtilleryResult = platoon.fight(
            Platoon(
                type = opponentPlatoon.type,
                count = reducedOpponentCount
            ),
            terrainType
        )

        return fightWithArtilleryResult == BattleResult.Win
    }
}
