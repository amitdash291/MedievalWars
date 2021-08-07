package models

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldContainExactly

class MedievalWarTest : WordSpec({
    "MedievalWar" When {
        "given two armies" Should {
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
                val medievalWar = MedievalWar(firstArmy, secondArmy, terrainTypes)

                medievalWar
                    .planBattleOrder()
                    .shouldContainExactly(
                        Platoon(PlatoonType.Militia, 30),
                        Platoon(PlatoonType.FootArcher, 20),
                        Platoon(PlatoonType.Spearmen, 10),
                        Platoon(PlatoonType.LightCavalry, 1000),
                        Platoon(PlatoonType.HeavyCavalry, 120),
                    )
            }
        }
        "given two armies and terrains" Should {
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
                val medievalWar = MedievalWar(firstArmy, secondArmy, terrainTypes)

                medievalWar
                    .planBattleOrder()
                    .shouldContainExactly(
                        Platoon(PlatoonType.HeavyCavalry, 5),
                        Platoon(PlatoonType.Militia, 10),
                        Platoon(PlatoonType.FootArcher, 5),
                        Platoon(PlatoonType.Spearmen, 10),
                        Platoon(PlatoonType.LightCavalry, 100),
                    )
            }
        }
    }
})