package models

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import models.enums.*
import models.enums.PlatoonType.*
import models.enums.TerrainType.Hill
import models.enums.TerrainType.Muddy

class PlatoonTest : WordSpec({
    "Platoon Fights - Militia" When {
        "fights with another Militia Platoon with less number of soldiers" Should {
            "return BattleResult.Win" {
                val platoon1 = Platoon(count = 100)
                val platoon2 = Platoon(count = 50)

                platoon1.fight(platoon2)
                    .shouldBe(BattleResult.Win)
            }
        }

        "fights with another Militia Platoon with equal number of soldiers" Should {
            "return BattleResult.Draw" {
                val platoon1 = Platoon(count = 100)
                val platoon2 = Platoon(count = 100)

                platoon1.fight(platoon2)
                    .shouldBe(BattleResult.Draw)
            }
        }

        "fights with another Militia Platoon with greater number of soldiers" Should {
            "return BattleResult.Loss" {
                val platoon1 = Platoon(count = 30)
                val platoon2 = Platoon(count = 50)

                platoon1.fight(platoon2)
                    .shouldBe(BattleResult.Loss)
            }
        }

        "fights with a Spearmen Platoon with less than double the number of Militia soldiers" Should {
            "return BattleResult.Win" {
                val militiaPlatoon = Platoon(type = Militia, count = 30)
                val spearmenPlatoon = Platoon(type = Spearmen, count = 59)

                militiaPlatoon.fight(spearmenPlatoon)
                    .shouldBe(BattleResult.Win)
            }
        }

        "fights with a Spearmen Platoon with double the number of Militia soldiers" Should {
            "return BattleResult.Draw" {
                val militiaPlatoon = Platoon(type = Militia, count = 30)
                val spearmenPlatoon = Platoon(type = Spearmen, count = 60)

                militiaPlatoon.fight(spearmenPlatoon)
                    .shouldBe(BattleResult.Draw)
            }
        }

        "fights with a Spearmen Platoon with more than double the number of Militia soldiers" Should {
            "return BattleResult.Loss" {
                val militiaPlatoon = Platoon(type = Militia, count = 30)
                val spearmenPlatoon = Platoon(type = Spearmen, count = 61)

                militiaPlatoon.fight(spearmenPlatoon)
                    .shouldBe(BattleResult.Loss)
            }
        }

        "fights with a HeavyCavalry Platoon with more than double the number of HeavyCavalry soldiers" Should {
            "return BattleResult.Win" {
                val militiaPlatoon = Platoon(type = Militia, count = 81)
                val heavyCavalryPlatoon = Platoon(type = HeavyCavalry, count = 40)

                militiaPlatoon.fight(heavyCavalryPlatoon)
                    .shouldBe(BattleResult.Win)
            }
        }

        "fights with a HeavyCavalry Platoon with double the number of HeavyCavalry soldiers" Should {
            "return BattleResult.Draw" {
                val militiaPlatoon = Platoon(type = Militia, count = 80)
                val heavyCavalryPlatoon = Platoon(type = HeavyCavalry, count = 40)

                militiaPlatoon.fight(heavyCavalryPlatoon)
                    .shouldBe(BattleResult.Draw)
            }
        }

        "fights with a HeavyCavalry Platoon with less than double the number of HeavyCavalry soldiers" Should {
            "return BattleResult.Loss" {
                val militiaPlatoon = Platoon(type = Militia, count = 79)
                val heavyCavalryPlatoon = Platoon(type = HeavyCavalry, count = 40)

                militiaPlatoon.fight(heavyCavalryPlatoon)
                    .shouldBe(BattleResult.Loss)
            }
        }
    }

    "Platoon Configuration - Militia" When {
        "getting configuration" Should {
            "return Militia Configuration" {
                Platoon(type = Militia)
                    .getConfiguration()
                    .shouldBe(
                        PlatoonConfiguration(
                            advantageAgainst = listOf(Spearmen, PlatoonType.LightCavalry),
                            terrainEffectMap = mapOf(
                                Hill to 0.5,
                                Muddy to 2.0,
                            )
                        )
                    )
            }
        }
    }

    "Platoon Strength - Militia" When {
        "opponent is Spearmen" Should {
            "return double of the count of soldiers" {
                Platoon(type = Militia, count = 100)
                    .getStrength(Platoon(Spearmen), TerrainType.Default)
                    .shouldBe(200.0)
            }
        }
        "terrain is Hill" Should {
            "return half of the count of soldiers" {
                Platoon(type = Militia, count = 100)
                    .getStrength(Platoon(Militia), Hill)
                    .shouldBe(50.0)
            }
        }
        "terrain is Hill and opponent is Spearmen" Should {
            "return equal count of soldiers" {
                Platoon(type = Militia, count = 100)
                    .getStrength(Platoon(Spearmen), Hill)
                    .shouldBe(100.0)
            }
        }
    }
})