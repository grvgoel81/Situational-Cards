package com.gaurav.situationalcards.model

import com.google.gson.annotations.SerializedName

/**
 * Created by gauravgoel on 20/February/2021
 */
data class CardApiResponse(
    @SerializedName("card_groups")
    val cardList: List<CardGroup>
)