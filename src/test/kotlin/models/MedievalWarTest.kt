package models

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldContainExactly

class MedievalWarTest : WordSpec({
    "MedievalWar" When {
        "given two armies" Should {
            "return the winning army" {
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
                val medievalWar = MedievalWar(firstArmy, secondArmy)

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
    }
})