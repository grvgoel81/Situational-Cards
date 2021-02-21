package com.gaurav.situationalcards.ui.adapter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.situationalcards.R
import com.gaurav.situationalcards.SituationalCardsApplication
import com.gaurav.situationalcards.model.Card
import com.gaurav.situationalcards.model.CardGroup
import com.gaurav.situationalcards.utils.ImageLoader
import com.gaurav.situationalcards.utils.SharedPreferencesHelper
import com.gaurav.situationalcards.utils.TextViewFormatter
import kotlinx.android.synthetic.main.action_long_press.view.*
import kotlinx.android.synthetic.main.item_big_display_card.view.*
import kotlinx.android.synthetic.main.item_dynamic_image_card.view.*
import kotlinx.android.synthetic.main.item_image_card.view.ic_card_view
import kotlinx.android.synthetic.main.item_small_card_arrow.view.*
import kotlinx.android.synthetic.main.item_small_display_card.view.*

/**
 * Created by gauravgoel on 20/February/2021
 */
class CardAdapter(
    private val groupId: Long,
    private val designType: CardGroup.DesignType
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardData = mutableListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        var resourceLayout = R.layout.item_big_display_card
        when (designType) {
            CardGroup.DesignType.BIG_DISPLAY_CARD -> {
                resourceLayout = if (viewType == SHOWMENU) {
                    R.layout.action_long_press
                } else {
                    R.layout.item_big_display_card
                }
            }

            CardGroup.DesignType.SMALL_CARD_WITH_ARROW ->
                resourceLayout = R.layout.item_small_card_arrow

            CardGroup.DesignType.IMAGE_CARD ->
                resourceLayout = R.layout.item_image_card

            CardGroup.DesignType.SMALL_DISPLAY_CARD ->
                resourceLayout = R.layout.item_small_display_card

            CardGroup.DesignType.DYNAMIC_WIDTH_CARD ->
                resourceLayout = R.layout.item_dynamic_image_card
        }
        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(resourceLayout, parent, false)
        )
    }

    override fun getItemCount(): Int = cardData.size

    override fun getItemViewType(position: Int): Int {
        return if (cardData[position].swipeMenu) {
            SHOWMENU
        } else {
            HIDEMENU
        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        when (designType) {

            CardGroup.DesignType.BIG_DISPLAY_CARD -> {
                if (!cardData[position].swipeMenu) {
                    holder.bindBigDisplayCard(cardData[position])
                    holder.itemView.setOnLongClickListener {
                        showMenu(position)
                        true
                    }
                } else {

                    holder.itemView.ll_dismiss.setOnClickListener {
                        deleteCard(position + 1)
                    }

                    holder.itemView.ll_remind.setOnClickListener {
                        deleteCard(position + 1)
                    }
                }
            }

            CardGroup.DesignType.SMALL_CARD_WITH_ARROW ->
                holder.bindSmallCardArrow(cardData[position])

            CardGroup.DesignType.IMAGE_CARD ->
                holder.bindImageCard(cardData[position])

            CardGroup.DesignType.SMALL_DISPLAY_CARD ->
                holder.bindSmallCard(cardData[position])

            CardGroup.DesignType.DYNAMIC_WIDTH_CARD ->
                holder.bindDynamicImageCard(cardData[position])
        }
    }

    fun setCardData(cards: ArrayList<Card>) {
        cardData.clear()
        cardData.addAll(cards)
        notifyDataSetChanged()
    }

    private fun deleteCard(position: Int) {
        if (cardData.size > position) {
            cardData.removeAt(position)
            notifyDataSetChanged()
            SharedPreferencesHelper.saveGroupId(groupId.toString())
        }
        hideMenu()
    }

    fun hideMenu() {
        if (cardData.isNotEmpty() && cardData[0].swipeMenu) {
            cardData.removeAt(0)
            notifyDataSetChanged()
        }
    }

    private fun showMenu(position: Int) {
        if (cardData.isNotEmpty() && !cardData[0].swipeMenu) {
            val menuCard = Card("menu_card")
            menuCard.swipeMenu = true
            cardData.add(position, menuCard)
            notifyDataSetChanged()
        }
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
        fun bindBigDisplayCard(card: Card) {
            TextViewFormatter.applyTextFormat(
                card.formattedTitle, itemView.bdc_tv_title, card.title
            )

            TextViewFormatter.applyTextFormat(
                card.formattedDescription, itemView.bdc_tv_description, card.description
            )

            card.bgImage?.imageUrl?.let {
                ImageLoader.loadImage(it, itemView, itemView.bdc_card_view)
            }

            card.ctaList?.get(0)?.let { cta ->
                itemView.bdc_btn_action.setBackgroundColor(Color.parseColor(cta.bgColor))
                itemView.bdc_btn_action.text = cta.text
                itemView.bdc_btn_action.setTextColor(Color.parseColor(cta.textColor))
                itemView.bdc_btn_action.setOnClickListener {
                    if (cta.url != null) {
                        performAction(cta.url)
                    } else if (cta.otherUrl != null) {
                        performAction(cta.otherUrl)
                    }
                }
            }
        }
        
        fun bindSmallCardArrow(card: Card) {
            TextViewFormatter.applyTextFormat(
                card.formattedTitle, itemView.sca_tv_title, card.title
            )

            card.icon?.imageUrl?.let {
                ImageLoader.loadImage(it, itemView, itemView.sca_iv_icon)
            }
            itemView.sca_card_view.setOnClickListener {
                if (card.url != null) {
                    performAction(card.url)
                }
            }
        }
        
        fun bindImageCard(card: Card) {
            card.bgImage?.imageUrl?.let {
                ImageLoader.loadImage(it, itemView, itemView.ic_card_view)
            }

            itemView.ic_card_view.setOnClickListener {
                if (card.url != null) {
                    performAction(card.url)
                }
            }
        }
        
        fun bindSmallCard(card: Card) {
            TextViewFormatter.applyTextFormat(
                card.formattedTitle, itemView.sdc_tv_title, card.title
            )

            TextViewFormatter.applyTextFormat(
                card.formattedDescription, itemView.sdc_tv_description, card.description
            )

            itemView.sdc_linear_layout.setBackgroundColor(Color.parseColor("#FFFFFF"))

            card.icon?.imageUrl?.let {
                ImageLoader.loadImage(it, itemView, itemView.sdc_iv_icon)
            }

            itemView.sdc_card_view.setOnClickListener {
                if (card.url != null) {
                    performAction(card.url)
                }
            }
        }

        fun bindDynamicImageCard(card: Card) {
            card.bgImage?.imageUrl?.let {
                ImageLoader.loadImage(it, itemView, itemView.dynamic_ic__card_view)
            }

            itemView.dynamic_ic__card_view.setOnClickListener {
                if (card.url != null) {
                    performAction(card.url)
                }
            }
        }

        private fun performAction(url: String): Boolean {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            SituationalCardsApplication.getContext().startActivity(intent)
            return true
        }
    }

    companion object {
        const val SHOWMENU = 1
        const val HIDEMENU = 2
    }
}