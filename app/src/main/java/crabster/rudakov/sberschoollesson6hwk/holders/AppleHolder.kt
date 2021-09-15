package crabster.rudakov.sberschoollesson6hwk.holders

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import crabster.rudakov.sberschoollesson6hwk.*
import crabster.rudakov.sberschoollesson6hwk.data.Baskets

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
            Baskets.baskets.remove(apple)
            var baskets = 0
            for (x in Baskets.baskets) {
                if (x is Basket) baskets++
                if (x is Summary) x.countOfApples = Baskets.baskets.size - baskets - 1
            }
            adapter.notifyDataSetChanged()
        }
    }

}