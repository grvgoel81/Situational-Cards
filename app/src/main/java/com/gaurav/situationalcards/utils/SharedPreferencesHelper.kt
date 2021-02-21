package com.gaurav.situationalcards.utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.gaurav.situationalcards.SituationalCardsApplication

/**
 * Created by gauravgoel on 20/February/2021
 */
object SharedPreferencesHelper {

    private const val SITUATIONAL_PREF_KEY = "situational_cards_pref"

    private fun defaultPrefs(): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(SituationalCardsApplication.getContext())


    fun saveGroupId(groupId: String) {
        val editor = defaultPrefs().edit()
        editor.putString(SITUATIONAL_PREF_KEY,
            defaultPrefs().getString(SITUATIONAL_PREF_KEY, "").plus(",").plus(groupId))
    }

    fun removeGroup(groupId: String): Boolean {
        val stringList = defaultPrefs().getString(SITUATIONAL_PREF_KEY, "")?.split(",")
        if(stringList != null) {
            for (id in stringList) {
                if(id == groupId) return true
            }
        } else {
            return false
        }
        return false
    }
}