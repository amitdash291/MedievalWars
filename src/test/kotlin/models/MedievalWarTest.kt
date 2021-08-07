package models

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import models.enums.PlatoonType
import models.enums.TerrainType
import models.exceptions.NoWinningPlanException

class MedievalWarTest : WordSpec({
    "MedievalWar" When {
        "given two armies with 5 platoons" Should {
            "return the winning order for the first army with at least 3 wins" {
                val firstArmy = listOf(
                    Platoon(PlatoonType.Spearmen, 10),
                    Platoon(PlatoonType.Militia, 30),
                    Platoon(PlatoonType.FootArcher, 20),
                    Platoon(PlatoonType.LightCavalry, 1000),
                    Platoon(PlatoonType.HeavyCavalry, 120),
                )
                val secondArmy = listOf(
                    Platoon(PlatoonType.Militia, 10),
                    Platoon(PlatoonType.Spearmen, 10),
                    Platoon(PlatoonType.FootArcher, 1000),
                    Platoon(PlatoonType.LightCavalry, 120),
                    Platoon(PlatoonType.CavalryArcher, 100),
                )
                val terrainTypes = listOf(
                    TerrainType.Default,
                    TerrainType.Default,
                    TerrainType.Default,
                    TerrainType.Default,
                    TerrainType.Default,
                )

                val actualPlan = MedievalWar(firstArmy, secondArmy, terrainTypes)
                    .planBattle()

                actualPlan.battleOrder
                    .shouldContainExactly(
                        Platoon(PlatoonType.Militia, 30),
                        Platoon(PlatoonType.FootArcher, 20),
                        Platoon(PlatoonType.Spearmen, 10),
                        Platoon(PlatoonType.LightCavalry, 1000),
                        Platoon(PlatoonType.HeavyCavalry, 120),
                    )
                actualPlan.artilleryPosition
                    .shouldBe(-1)
            }
        }

        "given two armies with 5 platoons and respective terrains" Should {
            "return the winning order for the first army with at least 3 wins" {
                val firstArmy = listOf(
                    Platoon(PlatoonType.Militia, 10),
                    Platoon(PlatoonType.FootArcher, 5),
                    Platoon(PlatoonType.HeavyCavalry, 5),
                    Platoon(PlatoonType.Spearmen, 10),
                    Platoon(PlatoonType.LightCavalry, 100),
                )
                val secondArmy = listOf(
                    Platoon(PlatoonType.Militia, 10),
                    Platoon(PlatoonType.Spearmen, 10),
                    Platoon(PlatoonType.FootArcher, 100),
                    Platoon(PlatoonType.LightCavalry, 120),
                    Platoon(PlatoonType.CavalryArcher, 10),
                )
                val terrainTypes = listOf(
                    TerrainType.Plains,
                    TerrainType.Default,
                    TerrainType.Default,
                    TerrainType.Default,
                    TerrainType.Default,
                )

                val actualPlan = MedievalWar(firstArmy, secondArmy, terrainTypes)
                    .planBattle()

                actualPlan.battleOrder
                    .shouldContainExactly(
                        Platoon(PlatoonType.HeavyCavalry, 5),
                        Platoon(PlatoonType.Militia, 10),
                        Platoon(PlatoonType.FootArcher, 5),
                        Platoon(PlatoonType.Spearmen, 10),
                        Platoon(PlatoonType.LightCavalry, 100),
                    )
                actualPlan.artilleryPosition
                    .shouldBe(-1)
            }
        }

        "given two armies with no winning plan" Should {
            "reinforce a platoon with artillery and return the winning plan including artillery position" {
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
                val terrainTypes = listOf(
                    TerrainType.Default,
                    TerrainType.Default,
                    TerrainType.Default,
                )

                val actualPlan = MedievalWar(
                    firstArmy = firstArmy,
                    secondArmy = secondArmy,
                    terrainTypes = terrainTypes
                ).planBattle()

                actualPlan.battleOrder
                    .shouldContainExactly(
                        Platoon(PlatoonType.Militia, 11),
                        Platoon(PlatoonType.Militia, 65),
                        Platoon(PlatoonType.Militia, 5),
                    )
                actualPlan.artilleryPosition
                    .shouldBe(0)
            }
        }

        "given two armies with no winning plan and no artillery reinforcement winning possibility" Should {
            "throw NoWinningPlanException" {
                val firstArmy = listOf(
                    Platoon(PlatoonType.Militia, 9),
                    Platoon(PlatoonType.Militia, 65),
                    Platoon(PlatoonType.Militia, 5),
                )
                val secondArmy = listOf(
                    Platoon(PlatoonType.Militia, 60),
                    Platoon(PlatoonType.Militia, 60),
                    Platoon(PlatoonType.Militia, 60),
                )
                val terrainTypes = listOf(
                    TerrainType.Default,
                    TerrainType.Default,
                    TerrainType.Default,
                )

                val actualException = shouldThrowExactly<NoWinningPlanException> {
                    MedievalWar(
                        firstArmy = firstArmy,
                        secondArmy = secondArmy,
                        terrainTypes = terrainTypes
                    ).planBattle()
                }

                actualException.message
                    .shouldBe("There is no chance of winning")
            }
        }
    }
})