package models

sealed class WarResult {
    object Victory : WarResult()
    object NoResultWithArtillery : WarResult()
    data class VictoryWithArtillery(val artilleryPosition: Int) : WarResult()
    object Loss : WarResult()
}