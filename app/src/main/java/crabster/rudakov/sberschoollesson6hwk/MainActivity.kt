package crabster.rudakov.sberschoollesson6hwk

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import crabster.rudakov.sberschoollesson6hwk.data.Baskets

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var appleBasketAdapter: AppleBasketAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appleBasketAdapter = AppleBasketAdapter(this)
        appleBasketAdapter.baskets = Baskets.baskets
        Baskets.addBaskets()

        recyclerView = findViewById(R.id.single_basket)
        recyclerView.adapter = appleBasketAdapter

        /**
         * По нажатию кнопки добавляем в начало списка новое View "Корзина".
         * */
        val buttonAddBasket: Button = findViewById(R.id.button_add_basket)
        buttonAddBasket.setOnClickListener {
            Baskets.baskets.add(0, Basket())
            appleBasketAdapter.notifyDataSetChanged()
        }

        /**
         * По нажатию кнопки удаляем все View "Корзина" и "Яблоко".
         * */
        val buttonRemoveAllBaskets: Button = findViewById(R.id.button_remove_all_baskets)
        buttonRemoveAllBaskets.setOnClickListener {
            Baskets.baskets.removeAll { e -> e is Basket || e is Apple }
            val summary: Summary = Baskets.baskets[Baskets.baskets.size - 1] as Summary
            summary.countOfApples = Baskets.baskets.size - 1
            appleBasketAdapter.notifyDataSetChanged()
        }

    }

}