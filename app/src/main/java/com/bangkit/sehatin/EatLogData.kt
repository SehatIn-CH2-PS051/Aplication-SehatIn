package com.bangkit.sehatin

data class EatLogData(
    val nama: String,
    val detail: Detail,
    val kalori: String,
    val porsi: String
)

data class Detail(
    val Karbohidrat: String,
    val Lemak: String,
    val Protein: String
)
