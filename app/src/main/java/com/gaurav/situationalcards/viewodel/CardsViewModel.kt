package com.gaurav.situationalcards.viewodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaurav.situationalcards.R
import com.gaurav.situationalcards.SituationalCardsApplication
import com.gaurav.situationalcards.data.Repository
import com.gaurav.situationalcards.model.CardApiResponse
import com.gaurav.situationalcards.model.CardGroup
import com.gaurav.situationalcards.utils.SharedPreferencesHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by gauravgoel on 20/February/2021
 */
class CardsViewModel : ViewModel() {

    private val repository: Repository = Repository()

    val cardGroup = MutableLiveData<List<CardGroup>>()

    lateinit var errorMessage: String

    fun fetchCardsData() {
        repository.getCardsData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CardApiResponse>() {
                override fun onComplete() {}

                override fun onNext(response: CardApiResponse?) {
                    cardGroup.value = response?.let { getRefinedList(it) }
                }

                override fun onError(e: Throwable?) {
                    errorMessage = SituationalCardsApplication.getContext().getString(R.string.error_messsage)
                }

            })
    }

    fun getRefinedList(cardApiResponse: CardApiResponse): List<CardGroup> {
        val refinedList = mutableListOf<CardGroup>()
        for(cardGroup in cardApiResponse.cardList) {
            if (!SharedPreferencesHelper.removeGroup(cardGroup.id.toString())) {
                refinedList.add(cardGroup)
            }
        }
        return refinedList
    }
}