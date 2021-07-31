package utils

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import models.Platoon
import models.PlatoonType

class ParserTest : WordSpec({
    "Army Input Parsing" When {
        "input is empty" Should {
            "return empty list"{
                Parser.getPlatoons("").shouldBeEmpty()
            }
        }

        "input contains Platoons data" Should {
            "return list of Platoon objects"{
                val expectedPlatoons = listOf(
                    Platoon(PlatoonType.FootArcher, 20),
                    Platoon(PlatoonType.LightCavalry, 1000),
                    Platoon(PlatoonType.HeavyCavalry, 120)
                )
                Parser
                    .getPlatoons("FootArcher#20;LightCavalry#1000;HeavyCavalry#120")
                    .shouldContainExactly(expectedPlatoons)
            }
        }

        "input contains Platoons data with unknown types" Should {
            "return list of Platoon objects with known types"{
                val expectedPlatoons = listOf(
                    Platoon(PlatoonType.FootArcher, 20),
                    Platoon(PlatoonType.HeavyCavalry, 120)
                )
                Parser
                    .getPlatoons("FootArcher#20;UnknownType#1000;HeavyCavalry#120")
                    .shouldContainExactly(expectedPlatoons)
            }
        }
    }

    "Army Output Parsing" When {
        "given a list of platoons" Should {
            "serialize them in the expected string format" {
                val platoons = listOf(
                    Platoon(PlatoonType.FootArcher, 20),
                    Platoon(PlatoonType.LightCavalry, 1000),
                    Platoon(PlatoonType.HeavyCavalry, 120)
                )
                Parser.serializePlatoons(platoons)
                    .shouldBe("FootArcher#20;LightCavalry#1000;HeavyCavalry#120")
            }
        }
    }
})