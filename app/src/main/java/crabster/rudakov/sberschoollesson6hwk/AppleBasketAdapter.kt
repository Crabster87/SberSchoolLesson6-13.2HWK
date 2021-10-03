package crabster.rudakov.sberschoollesson6hwk

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import crabster.rudakov.sberschoollesson6hwk.data.Apple
import crabster.rudakov.sberschoollesson6hwk.data.Basket
import crabster.rudakov.sberschoollesson6hwk.data.Elements
import crabster.rudakov.sberschoollesson6hwk.data.Summary
import crabster.rudakov.sberschoollesson6hwk.holders.AppleHolder
import crabster.rudakov.sberschoollesson6hwk.holders.BasketHolder
import crabster.rudakov.sberschoollesson6hwk.holders.Holder
import crabster.rudakov.sberschoollesson6hwk.holders.SummaryHolder

/**
 * Класс Адаптер для списка.
 * */
class AppleBasketAdapter(private val context: Context) : RecyclerView.Adapter<Holder>() {

    var baskets: List<Elements> = mutableListOf()

    /**
     * В зависимости от типа View устанавливаем соответствующий Layout.
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return when (viewType) {
            BASKET -> BasketHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_main_item_basket, parent, false), context
            )
            APPLE -> AppleHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_main_item_apple, parent, false)
            )
            SUMMARY -> SummaryHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_main_item_summary, parent, false)
            )
            else -> TODO()
        }
    }

    /**
     * Распозанём тип View.
     * */
    override fun getItemViewType(position: Int): Int {
        return when (baskets[position]) {
            is Basket -> BASKET
            is Apple -> APPLE
            is Summary -> SUMMARY
            else -> TODO("")
        }
    }

    /**
     * В зависимости от типа View привязываем данные через соответствующий Holder.
     * */
    override fun onBindViewHolder(holder: Holder, position: Int) {

        when (holder) {
            is BasketHolder -> {
                val basket = baskets[position]
                basket as Basket
                holder.bind(basket, this)
            }
            is AppleHolder -> {
                val apple = baskets[position]
                apple as Apple
                holder.bind(apple, this)
            }
            is SummaryHolder -> {
                val summary = baskets[position]
                summary as Summary
                holder.bind(summary)
            }
        }
    }

    /**
     * Возвращаем количество элементов списка.
     * */
    override fun getItemCount(): Int {
        return baskets.size
    }

    companion object {
        const val BASKET: Int = 0
        const val APPLE: Int = 1
        const val SUMMARY: Int = 2
    }

}