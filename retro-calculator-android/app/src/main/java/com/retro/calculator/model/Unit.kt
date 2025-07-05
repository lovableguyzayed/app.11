package com.retro.calculator.model

enum class Unit(val displayName: String, val category: String) {
    KG("kg", "weight"),
    GRAM("gram", "weight"),
    QUINTAL("quintal", "weight"),
    TON("ton", "weight"),
    LITER("liter", "volume"),
    ML("ml", "volume"),
    GALLON("gallon", "volume"),
    PIECE("piece", "count"),
    DOZEN("dozen", "count"),
    BOX("box", "count");
    
    companion object {
        fun getAllUnits(): List<Unit> = values().toList()
        
        fun getWeightUnits(): List<Unit> = values().filter { it.category == "weight" }
        fun getVolumeUnits(): List<Unit> = values().filter { it.category == "volume" }
        fun getCountUnits(): List<Unit> = values().filter { it.category == "count" }
    }
}