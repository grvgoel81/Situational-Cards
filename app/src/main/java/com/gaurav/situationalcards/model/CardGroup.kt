package com.gaurav.situationalcards.model

import com.google.gson.annotations.SerializedName

/**
 * Created by gauravgoel on 20/February/2021
 */
data class CardGroup(
    val id: Long,
    val name: String,
    @SerializedName("design_type")
    val designType: DesignType,
    @SerializedName("card_type")
    val cardType: Long,
    @SerializedName("cards")
    val cardList: List<Card>,
    @SerializedName("is_scrollable")
    val isScrollable: Boolean
) {

    enum class DesignType {
        @SerializedName("HC1")
        SMALL_DISPLAY_CARD,

        @SerializedName("HC3")
        BIG_DISPLAY_CARD,

        @SerializedName("HC5")
        IMAGE_CARD,

        @SerializedName("HC6")
        SMALL_CARD_WITH_ARROW,

        @SerializedName("HC9")
        DYNAMIC_WIDTH_CARD,
    }
}