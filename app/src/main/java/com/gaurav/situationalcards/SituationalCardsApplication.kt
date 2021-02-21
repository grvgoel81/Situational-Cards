package com.gaurav.situationalcards

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

/**
 * Created by gauravgoel on 20/February/2021
 */
class SituationalCardsApplication : Application() {

    companion object {

        lateinit var context: SituationalCardsApplication

        fun getContext(): Context = context.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}