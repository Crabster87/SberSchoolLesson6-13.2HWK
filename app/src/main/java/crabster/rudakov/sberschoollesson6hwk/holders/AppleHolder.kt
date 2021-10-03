package crabster.rudakov.sberschoollesson6hwk.holders

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import crabster.rudakov.sberschoollesson6hwk.*
import crabster.rudakov.sberschoollesson6hwk.InteractiveHelper.Companion.undoAppleAnimationEffect
import crabster.rudakov.sberschoollesson6hwk.data.Apple
import crabster.rudakov.sberschoollesson6hwk.data.Basket
import crabster.rudakov.sberschoollesson6hwk.data.Baskets
import crabster.rudakov.sberschoollesson6hwk.data.Summary

/**
 * Адаптер для View "Яблоко".
 * */
class AppleHolder(itemView: View) : Holder(itemView) {

    /**
     * По нажатию кнопки удаляем текущее View "Яблоко". Подсчитываем количество корзин для учета
     * общего размера списка, по нему вычитываем количество яблок. Передаём все изменеия во View
     * "Итоги" через адаптер.
     * */
    @SuppressLint("NotifyDataSetChanged")
    fun bind(apple: Apple, adapter: AppleBasketAdapter) {
        val buttonOfApple = itemView.findViewById<Button>(R.id.button_of_apple)
        buttonOfApple.setOnClickListener {
            removeApple(apple)
            undoAnimationEffect()
            adapter.notifyItemRemoved(layoutPosition)
            adapter.notifyItemChanged(Baskets.baskets.size - 1)
        }
    }

    /**
     * Метод удаляет объекты "Яблоко", подсчитывая их оставшееся количество, и передавая это
     * количество во View "Сумма".
     * */
    private fun removeApple(apple: Apple) {
        Baskets.baskets.remove(apple)
        var baskets = 0
        for (x in Baskets.baskets) {
            if (x is Basket) baskets++
            if (x is Summary) x.countOfApples = Baskets.baskets.size - baskets - 1
        }
    }

    /**
     * Метод отменяет применение анимации ко View "Яблоко".
     * */
    private fun undoAnimationEffect() {
        val imageOfApple = itemView.findViewById<ImageView>(R.id.apple_view)
        undoAppleAnimationEffect(imageOfApple)
    }

}