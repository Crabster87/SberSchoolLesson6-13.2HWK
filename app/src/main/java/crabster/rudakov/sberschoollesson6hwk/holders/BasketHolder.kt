package crabster.rudakov.sberschoollesson6hwk.holders

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import crabster.rudakov.sberschoollesson6hwk.*
import crabster.rudakov.sberschoollesson6hwk.InteractiveHelper.Companion.makeToastMessage
import crabster.rudakov.sberschoollesson6hwk.data.Apple
import crabster.rudakov.sberschoollesson6hwk.data.Basket
import crabster.rudakov.sberschoollesson6hwk.data.Baskets
import crabster.rudakov.sberschoollesson6hwk.data.Summary

/**
 * Адаптер для View "Корзина".
 * */
class BasketHolder(itemView: View, private val context: Context) : Holder(itemView) {

    /**
     * Устанавливаем характеристику корзины в TextView.
     * */
    fun bind(basket: Basket, adapter: AppleBasketAdapter) {
        val basketName = itemView.findViewById<TextView>(R.id.description_of_basket)
        basketName.text = basket.basketName

        val buttonOfBasket = itemView.findViewById<Button>(R.id.button_of_basket)
        buttonOfBasket.setOnClickListener {
            answerOnCLick(adapter)
        }
    }

    /**
     * По нажатию кнопки добавляем ниже по списку View "Яблоко", но не более 3 (в случае если
     * следующие от текущей корзины 3 элемента яблоки, то добавление не происходит, пользователь
     * видит укоряющее тост-сообщение. Подсчитываем итоговое количество яблок. Передаём все
     * изменеия во View "Итоги" через адаптер.
     * */
    @SuppressLint("NotifyDataSetChanged")
    private fun answerOnCLick(adapter: AppleBasketAdapter) {
        if (Baskets.baskets[layoutPosition + 1] is Apple &&
            Baskets.baskets[layoutPosition + 2] is Apple &&
            Baskets.baskets[layoutPosition + 3] is Apple
        ) {
            makeToastMessage(context, R.string.unlimit_creating_message)
        } else {
            Baskets.baskets.add(layoutPosition + 1, Apple())
            val summary: Summary = Baskets.baskets[Baskets.baskets.size - 1] as Summary
            summary.countOfApples++
            adapter.notifyItemInserted(layoutPosition + 1)
            adapter.notifyItemChanged(Baskets.baskets.size - 1)
        }
    }

}
