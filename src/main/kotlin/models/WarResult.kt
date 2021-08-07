package models

sealed class WarResult {
    object Victory : WarResult()
    data class VictoryWithArtillery(val artilleryPosition: Int) : WarResult()
    object Loss : WarResult()
}