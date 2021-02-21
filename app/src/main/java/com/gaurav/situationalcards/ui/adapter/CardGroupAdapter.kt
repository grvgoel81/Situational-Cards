package com.gaurav.situationalcards.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.situationalcards.R
import com.gaurav.situationalcards.model.Card
import com.gaurav.situationalcards.model.CardGroup
import com.gaurav.situationalcards.ui.CardViewHolder

/**
 * Created by gauravgoel on 20/February/2021
 */
class CardGroupAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var groupData = mutableListOf<CardGroup>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.layout_cards, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int = groupData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindView(holder as CardViewHolder, position)
    }

    fun setData(groups: ArrayList<CardGroup>) {
        groupData.clear()
        groupData.addAll(groups)
        notifyDataSetChanged()
    }

    private fun bindView(holder: CardViewHolder, position: Int) {
        val cardAdapter = CardAdapter(groupData[position].id, groupData[position].designType)

        holder.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = cardAdapter
        }

        cardAdapter.setCardData(groupData[position].cardList as ArrayList<Card>)

        holder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
                        cardAdapter.hideMenu()
                    }
                }
            }
        })
    }
}