package com.gaurav.situationalcards.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gaurav.situationalcards.R
import com.gaurav.situationalcards.model.CardGroup
import com.gaurav.situationalcards.ui.adapter.CardGroupAdapter
import com.gaurav.situationalcards.viewodel.CardsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val cardsViewModel by lazy {
        ViewModelProvider(this).get(CardsViewModel::class.java)
    }
    private lateinit var cardGroupAdapter: CardGroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardGroupAdapter = CardGroupAdapter(this)
        rv_sectional_cards.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cardGroupAdapter
        }

        swipe_refresh_layout.setOnRefreshListener(this)
        getCardsData()
    }

    override fun onRefresh() {
        showLoading()
        cardsViewModel.fetchCardsData()
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private fun showLoading() {
        error_view.visibility = View.GONE
        rv_sectional_cards.visibility = View.GONE
        swipe_refresh_layout.isRefreshing = false
    }

    private fun showErrorView() {
        error_view.visibility = View.VISIBLE
        rv_sectional_cards.visibility = View.GONE
        swipe_refresh_layout.isRefreshing = false
        tv_error_msg.text = cardsViewModel.errorMessage
    }

    private fun getCardsData() {
        cardsViewModel.cardGroup.observe(this, Observer { cardsList ->
            if(cardsList.isNotEmpty()) {
                setCardsData(cardsList)
            } else {
                showErrorView()
            }
        })
    }

    private fun setCardsData(cardsData: List<CardGroup>) {
        error_view.visibility = View.GONE
        rv_sectional_cards.visibility = View.VISIBLE
        swipe_refresh_layout.isRefreshing = false
        cardGroupAdapter.setData(cardsData as ArrayList<CardGroup>)
    }
}