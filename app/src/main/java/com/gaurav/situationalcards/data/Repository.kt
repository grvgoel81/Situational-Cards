package com.gaurav.situationalcards.data

import com.gaurav.situationalcards.model.CardApiResponse
import io.reactivex.rxjava3.core.Observable

class Repository {

    private val apiManager = ApiManager.instance

    fun getCardsData(): Observable<CardApiResponse> {
        return apiManager.apiService.fetchCardsData()
    }
}