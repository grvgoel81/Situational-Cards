package com.gaurav.situationalcards.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.situationalcards.R

/**
 * Created by gauravgoel on 20/February/2021
 */
class CardViewHolder internal constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var recyclerView: RecyclerView = itemView.findViewById<View>(R.id.rv_card) as RecyclerView
}