package models

data class BattlePlan(
    val battleOrder: List<Platoon>,
    val artilleryPosition: Int
)