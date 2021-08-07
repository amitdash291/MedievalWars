package strategies

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import models.*
import models.enums.PlatoonType
import models.enums.TerrainType

class WarStrategyTest : WordSpec({
    "WarStrategy - WarResult" When {
        "given two armies and a set of winning positions for the first army" Should {
            "return WarResult.Victory" {
                val firstArmy = listOf(
                    Platoon(type = PlatoonType.Militia, count = 10),
                    Platoon(type = PlatoonType.Militia, count = 20),
                    Platoon(type = PlatoonType.Militia, count = 30),
                )
                val secondArmy = listOf(
                    Platoon(type = PlatoonType.Militia, count = 15),
                    Platoon(type = PlatoonType.Militia, count = 9),
                    Platoon(type = PlatoonType.Militia, count = 25),
                )

                WarStrategy.getWarResult(
                    firstArmy = firstArmy,
                    secondArmy = secondArmy,
                    terrainTypes = getDefaultTerrainTypes(firstArmy.size),
                    firstArmyPositions = listOf(1, 0, 2)
                )
                    .shouldBe(WarResult.Victory)
            }
        }

        "given two armies and a set of losing positions for the first army" Should {
            "return WarResult.Loss" {
                val firstArmy = listOf(
                    Platoon(type = PlatoonType.Militia, count = 10),
                    Platoon(type = PlatoonType.Militia, count = 20),
                    Platoon(type = PlatoonType.Militia, count = 30),
                    Platoon(type = PlatoonType.Militia, count = 10),
                )
                val secondArmy = listOf(
                    Platoon(type = PlatoonType.Militia, count = 15),
                    Platoon(type = PlatoonType.Militia, count = 9),
                    Platoon(type = PlatoonType.Militia, count = 35),
                    Platoon(type = PlatoonType.Militia, count = 15),
                )

                WarStrategy.getWarResult(
                    firstArmy = firstArmy,
                    secondArmy = secondArmy,
                    terrainTypes = getDefaultTerrainTypes(firstArmy.size),
                    firstArmyPositions = listOf(0, 1, 2, 3)
                )
                    .shouldBe(WarResult.Loss)
            }
        }

        "given two armies and a set of winning positions for the first army with artillery" Should {
            "return WarResult.VictoryWithArtillery" {
                val firstArmy = listOf(
                    Platoon(PlatoonType.Militia, 11),
                    Platoon(PlatoonType.Militia, 65),
                    Platoon(PlatoonType.Militia, 5),
                )
                val secondArmy = listOf(
                    Platoon(PlatoonType.Militia, 60),
                    Platoon(PlatoonType.Militia, 60),
                    Platoon(PlatoonType.Militia, 60),
                )

                val actualWarResult = WarStrategy.getWarResult(
                    firstArmy = firstArmy,
                    secondArmy = secondArmy,
                    terrainTypes = getDefaultTerrainTypes(3),
                    firstArmyPositions = listOf(0, 1, 2)
                )

                actualWarResult.shouldBe(WarResult.VictoryWithArtillery(0))
            }
        }
    }
})

fun getDefaultTerrainTypes(armySize: Int) = (1..armySize)
    .map { TerrainType.Default }
