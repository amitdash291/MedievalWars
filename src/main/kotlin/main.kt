import models.MedievalWar
import models.TerrainType
import utils.Parser
import java.io.File

fun main(args: Array<String>) {
    val armies = File("src/main/kotlin/input.txt")
        .readLines()
    val medievalWar = MedievalWar(
        firstArmy = Parser.getPlatoons(armies[0]),
        secondArmy = Parser.getPlatoons(armies[1]),
        listOf(
            TerrainType.Default,
            TerrainType.Default,
            TerrainType.Default,
            TerrainType.Default,
            TerrainType.Default,
        )
    )

    try {
        val winningArmy = medievalWar.planBattleOrder()
        println(Parser.serializePlatoons(winningArmy))
    } catch(ex: Exception) {
        println(ex.message)
    }
}