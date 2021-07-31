package models

import utils.MathsUtil

data class MedievalWar(val firstArmy: List<Platoon>, val secondArmy: List<Platoon>) {
    fun planBattleOrder(): List<Platoon> {
        val permutatedPositionsList = MathsUtil.generatePermutations(firstArmy.size)

        for(positions in permutatedPositionsList) {
            var winCount = 0
            for(i in positions.indices) {
                val platoon1 = firstArmy[positions[i] - 1]
                val platoon2 = secondArmy[i]
                val result = platoon1 fight platoon2
                winCount += if(result == BattleResult.Win) 1 else 0
                if(winCount > 2) return positions.map { p -> firstArmy[p - 1] }
            }
        }

        throw Exception("There is no chance of winning")
    }
}