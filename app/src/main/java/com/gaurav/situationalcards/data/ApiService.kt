package com.gaurav.situationalcards.data

import com.gaurav.situationalcards.data.ApiHelper.API_KEY
import com.gaurav.situationalcards.model.CardApiResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET


interface ApiService {
    @GET(API_KEY)
    fun fetchCardsData(): Observable<CardApiResponse>
}