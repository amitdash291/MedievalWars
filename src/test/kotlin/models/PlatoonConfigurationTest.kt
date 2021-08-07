package models

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class PlatoonConfigurationTest : WordSpec({
    "PlatoonConfiguration - Militia" When {
        val militiaConfig = Platoon(type = PlatoonType.Militia).getConfiguration()

        "getting advantage factor over Spearmen / LightCavalry" Should {
            "return 2" {
                militiaConfig
                    .getAdvantageFactorOver(PlatoonType.Spearmen)
                    .shouldBe(2)
                militiaConfig
                    .getAdvantageFactorOver(PlatoonType.LightCavalry)
                    .shouldBe(2)
            }
        }

        "getting advantage factor over Militia / HeavyCavalry / FootArcher / CavalryArcher" Should {
            "return 1" {
                militiaConfig
                    .getAdvantageFactorOver(PlatoonType.Militia)
                    .shouldBe(1)
                militiaConfig
                    .getAdvantageFactorOver(PlatoonType.HeavyCavalry)
                    .shouldBe(1)
                militiaConfig
                    .getAdvantageFactorOver(PlatoonType.FootArcher)
                    .shouldBe(1)
                militiaConfig
                    .getAdvantageFactorOver(PlatoonType.CavalryArcher)
                    .shouldBe(1)
            }
        }

        "getting terrain effects for Default / Plains" Should {
            "return 1" {
                militiaConfig
                    .getTerrainEffect(TerrainType.Default)
                    .shouldBe(1.0)
                militiaConfig
                    .getTerrainEffect(TerrainType.Plains)
                    .shouldBe(1.0)
            }
        }

        "getting terrain effects for Hill" Should {
            "return 0.5" {
                militiaConfig
                    .getTerrainEffect(TerrainType.Hill)
                    .shouldBe(0.5)
            }
        }

        "getting terrain effects for Muddy" Should {
            "return 2.0" {
                militiaConfig
                    .getTerrainEffect(TerrainType.Muddy)
                    .shouldBe(2.0)
            }
        }

    }
})