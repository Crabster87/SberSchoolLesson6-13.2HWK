package crabster.rudakov.sberschoollesson6hwk.holders

import android.view.View
import android.widget.TextView
import crabster.rudakov.sberschoollesson6hwk.R
import crabster.rudakov.sberschoollesson6hwk.Summary

/**
 * Адаптер для View "Итоги".
 * */
class SummaryHolder(itemView: View) : Holder(itemView) {

    /**
     * Устанавливает итоговое количество яблок в текущее View.
     * */
    fun bind(summary: Summary) {
        val countOfApplesTextView = itemView.findViewById<TextView>(R.id.total)
        countOfApplesTextView.text = summary.countOfApples.toString()
    }

}