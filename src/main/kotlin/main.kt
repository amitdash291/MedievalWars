import models.MedievalWar
import utils.Parser
import java.io.File

fun main() {
    val armies = File("src/main/kotlin/input.txt")
        .readLines()
    val firstArmy = Parser.getPlatoons(armies[0])
    val medievalWar = MedievalWar(
        firstArmy = firstArmy,
        secondArmy = Parser.getPlatoons(armies[1]),
        terrainTypes = Parser.getTerrainTypes(armies[2])
    )

    try {
        val battlePlan = medievalWar.planBattle()
        println(Parser.serializePlatoons(battlePlan.battleOrder))
        println(Parser.serializeArtillery(firstArmy.size, battlePlan.artilleryPosition))
    } catch (ex: Exception) {
        println(ex.message)
    }
}
