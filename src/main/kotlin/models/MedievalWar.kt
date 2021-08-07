package models

import utils.MathsUtil

data class MedievalWar(
    val firstArmy: List<Platoon>,
    val secondArmy: List<Platoon>,
    val terrainTypes: List<TerrainType>
) {
    fun planBattleOrder(): List<Platoon> {
        val permutatedPositionsList = MathsUtil.generatePermutations(firstArmy.indices)

        for (positions in permutatedPositionsList) {
            var winCount = 0
            for (i in positions.indices) {
                val platoon = firstArmy[positions[i]]
                val opponentPlatoon = secondArmy[i]
                val terrainType = terrainTypes[i]
                val result = platoon.fight(opponentPlatoon, terrainType)
                winCount += if (result == BattleResult.Win) 1 else 0
                if (isBattleWon(winCount)) return positions.map { p -> firstArmy[p] }
            }
        }

        throw Exception("There is no chance of winning")
    }

    private fun isBattleWon(winCount: Int) = winCount > firstArmy.size / 2
}