package com.gaurav.situationalcards.model

import com.google.gson.annotations.SerializedName

/**
 * Created by gauravgoel on 20/February/2021
 */
data class Card(
    @SerializedName("name")
    val name: String,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("formatted_title")
    val formattedTitle: FormattedText? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("formatted_description")
    val formattedDescription: FormattedText? = null,
    val url: String? = null,
    @SerializedName("bg_image")
    val bgImage: CardImage? = null,
    @SerializedName("bg_color")
    val bgColor: String? = null,
    @SerializedName("cta")
    val ctaList: List<Cta>? = null,
    val icon: CardImage? = null,
    @SerializedName("bg_gradient")
    val bgGradient: Gradient? = null,
    var swipeMenu: Boolean = false
)