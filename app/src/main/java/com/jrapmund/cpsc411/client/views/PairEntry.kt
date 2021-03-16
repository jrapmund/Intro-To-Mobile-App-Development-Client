package com.jrapmund.cpsc411.client.views

import android.content.Context
import android.graphics.Typeface
import android.widget.TableRow
import android.widget.TextView

class PairEntry(context: Context?, val first:String, val second:String) : TableRow(context) {

    init {
        val col1 = LayoutParams(1)
        col1.weight = 1f

        val storeNameText = TextView(context)
        storeNameText.text = first
        storeNameText.typeface = Typeface.DEFAULT_BOLD
        storeNameText.layoutParams = col1
        this.addView(storeNameText)

        val col2 = LayoutParams(2)
        col2.weight = 1f
        val storeIdText = TextView(context)
        storeIdText.text = second
        storeIdText.textAlignment = TEXT_ALIGNMENT_TEXT_END
        storeIdText.layoutParams = col2
        this.addView(storeIdText)

        this.isClickable = true
    }
}