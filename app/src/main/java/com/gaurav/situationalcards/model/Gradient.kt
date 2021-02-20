package com.gaurav.situationalcards.model

import com.google.gson.annotations.SerializedName

data class Gradient(
    val angle: Long,
    @SerializedName("colors")
    val colorList: List<String>
)
