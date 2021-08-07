package models

import models.Constants.Artillery
import models.Constants.DEFAULT_POSITION
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
            var artilleryPosition = DEFAULT_POSITION

            for (currentPosition in positions.indices) {
                val platoon = firstArmy[positions[currentPosition]]
                val opponentPlatoon = secondArmy[currentPosition]
                val terrainType = terrainTypes[currentPosition]
                val originalResult = platoon.fight(opponentPlatoon, terrainType)

                if (originalResult == BattleResult.Win) {
                    winCount++
                } else if (artilleryPosition == DEFAULT_POSITION && Toggles.ARTILLERY) {
                    artilleryPosition =
                        if (canWinBattleWithArtillery(platoon, opponentPlatoon, terrainType))
                            currentPosition
                        else
                            DEFAULT_POSITION
                }

                if (isBattleWon(winCount)) {
                    return BattlePlan(
                        battleOrder = positions.map { p -> firstArmy[p] },
                        artilleryPosition = DEFAULT_POSITION
                    )
                }
            }

            if (isBattleWonWithArtillery(artilleryPosition, winCount)) {
                battlePlanWithArtillery = BattlePlan(
                    battleOrder = positions.map { p -> firstArmy[p] },
                    artilleryPosition = artilleryPosition
                )
            }
        }

        return battlePlanWithArtillery ?: throw Exception("There is no chance of winning")
    }

    private fun isBattleWon(winCount: Int) = winCount > firstArmy.size / 2

    private fun isBattleWonWithArtillery(artilleryPosition: Int, winCount: Int) =
        artilleryPosition > DEFAULT_POSITION &&
                winCount == firstArmy.size / 2 &&
                Toggles.ARTILLERY

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