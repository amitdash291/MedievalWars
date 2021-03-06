package utils

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import models.Platoon
import models.enums.PlatoonType
import models.enums.TerrainType

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

    "Terrain types Input Parsing" When {
        "input contains terrain types data" Should {
            "return a list of TerrainTypes" {
                Parser.getTerrainTypes("Plains;Default;Muddy;Hill")
                    .shouldContainExactly(
                        listOf(
                            TerrainType.Plains,
                            TerrainType.Default,
                            TerrainType.Muddy,
                            TerrainType.Hill,
                        )
                    )
            }
        }
    }

    "Artillery Output Parsing" When {
        "army size is 5 and artillery position is 3" Should {
            "serialize output as ...A." {
                Parser.serializeArtillery(
                    armySize = 5,
                    artilleryPosition = 3
                ).shouldBe("...A.")
            }
        }
    }
})