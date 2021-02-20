package com.gaurav.situationalcards.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.gaurav.situationalcards.model.FormattedText

/**
 * Created by gauravgoel on 20/February/2021
 */
object TextViewFormatter {

    fun applyTextFormat(
        formattedText: FormattedText?,
        textView: TextView,
        callBackText: String?
    ) {

        if (formattedText == null) {
            textView.text = callBackText
        } else {
            val spannableStringBuilder = SpannableStringBuilder()
            for (index in formattedText.entityList.indices) {
                val entity = formattedText.entityList[index]
                val colorCode = Color.parseColor(formattedText.entityList[index].color)
                var spannableString = SpannableString("${entity.text} ")

                spannableString.setSpan(
                    ForegroundColorSpan(colorCode),
                    0,
                    entity.text.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableStringBuilder.append(spannableString)
            }
            if (spannableStringBuilder.isEmpty()) {
                textView.text = callBackText
            } else {
                textView.text = spannableStringBuilder
            }
        }
    }
}