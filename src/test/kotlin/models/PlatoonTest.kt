package models

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

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
                val militiaPlatoon = Platoon(type = PlatoonType.Militia, count = 30)
                val spearmenPlatoon = Platoon(type = PlatoonType.Spearmen, count = 59)

                militiaPlatoon.fight(spearmenPlatoon)
                    .shouldBe(BattleResult.Win)
            }
        }

        "fights with a Spearmen Platoon with double the number of Militia soldiers" Should {
            "return BattleResult.Draw" {
                val militiaPlatoon = Platoon(type = PlatoonType.Militia, count = 30)
                val spearmenPlatoon = Platoon(type = PlatoonType.Spearmen, count = 60)

                militiaPlatoon.fight(spearmenPlatoon)
                    .shouldBe(BattleResult.Draw)
            }
        }

        "fights with a Spearmen Platoon with more than double the number of Militia soldiers" Should {
            "return BattleResult.Loss" {
                val militiaPlatoon = Platoon(type = PlatoonType.Militia, count = 30)
                val spearmenPlatoon = Platoon(type = PlatoonType.Spearmen, count = 61)

                militiaPlatoon.fight(spearmenPlatoon)
                    .shouldBe(BattleResult.Loss)
            }
        }

        "fights with a HeavyCavalry Platoon with double the number of HeavyCavalry soldiers" Should {
            "return BattleResult.Draw" {
                val militiaPlatoon = Platoon(type = PlatoonType.Militia, count = 80)
                val spearmenPlatoon = Platoon(type = PlatoonType.HeavyCavalry, count = 40)

                militiaPlatoon.fight(spearmenPlatoon)
                    .shouldBe(BattleResult.Draw)
            }
        }
    }

    "Platoon Advantages - Militia" When {
        "getting advantage factor over Spearmen / LightCavalry" Should {
            "return 2" {
                Platoon(type = PlatoonType.Militia)
                    .getAdvantageFactorOver(Platoon(type = PlatoonType.Spearmen))
                    .shouldBe(2)
                Platoon(type = PlatoonType.Militia)
                    .getAdvantageFactorOver(Platoon(type = PlatoonType.LightCavalry))
                    .shouldBe(2)
            }
        }

        "getting advantage factor over Militia / HeavyCavalry / FootArcher / CavalryArcher" Should {
            "return 1" {
                Platoon(type = PlatoonType.Militia)
                    .getAdvantageFactorOver(Platoon(type = PlatoonType.Militia))
                    .shouldBe(1)
                Platoon(type = PlatoonType.Militia)
                    .getAdvantageFactorOver(Platoon(type = PlatoonType.HeavyCavalry))
                    .shouldBe(1)
                Platoon(type = PlatoonType.Militia)
                    .getAdvantageFactorOver(Platoon(type = PlatoonType.FootArcher))
                    .shouldBe(1)
                Platoon(type = PlatoonType.Militia)
                    .getAdvantageFactorOver(Platoon(type = PlatoonType.CavalryArcher))
                    .shouldBe(1)
            }
        }

        "getting disdvantage factor over HeavyCavalry / FootArcher" Should {
            "return 2" {
                Platoon(type = PlatoonType.Militia)
                    .getDisadvantageFactorOver(Platoon(type = PlatoonType.HeavyCavalry))
                    .shouldBe(2)
                Platoon(type = PlatoonType.Militia)
                    .getDisadvantageFactorOver(Platoon(type = PlatoonType.FootArcher))
                    .shouldBe(2)
            }
        }

        "getting advantage factor over Militia / Spearmen / LightCavalry / CavalryArcher" Should {
            "return 1" {
                Platoon(type = PlatoonType.Militia)
                    .getDisadvantageFactorOver(Platoon(type = PlatoonType.Militia))
                    .shouldBe(1)
                 Platoon(type = PlatoonType.Militia)
                    .getDisadvantageFactorOver(Platoon(type = PlatoonType.Spearmen))
                    .shouldBe(1)
                Platoon(type = PlatoonType.Militia)
                    .getDisadvantageFactorOver(Platoon(type = PlatoonType.LightCavalry))
                    .shouldBe(1)
                Platoon(type = PlatoonType.Militia)
                    .getDisadvantageFactorOver(Platoon(type = PlatoonType.CavalryArcher))
                    .shouldBe(1)
            }
        }

    }
})