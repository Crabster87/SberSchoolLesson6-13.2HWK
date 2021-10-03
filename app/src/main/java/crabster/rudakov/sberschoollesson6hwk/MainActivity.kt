package crabster.rudakov.sberschoollesson6hwk

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import crabster.rudakov.sberschoollesson6hwk.data.Baskets
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import crabster.rudakov.sberschoollesson6hwk.holders.AppleHolder
import crabster.rudakov.sberschoollesson6hwk.holders.BasketHolder
import crabster.rudakov.sberschoollesson6hwk.holders.SummaryHolder

import crabster.rudakov.sberschoollesson6hwk.InteractiveHelper.Companion.makeAppleAnimation
import crabster.rudakov.sberschoollesson6hwk.InteractiveHelper.Companion.makeToastMessage
import crabster.rudakov.sberschoollesson6hwk.InteractiveHelper.Companion.undoAppleAnimationEffect
import crabster.rudakov.sberschoollesson6hwk.data.Apple
import crabster.rudakov.sberschoollesson6hwk.data.Basket
import crabster.rudakov.sberschoollesson6hwk.data.Summary


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
        recyclerView.layoutManager = MyLinearLayoutManager(this)
        recyclerView.adapter = appleBasketAdapter

        /**
         * По нажатию кнопки добавляем в начало списка новое View "Корзина".
         * */
        val buttonAddBasket: Button = findViewById(R.id.button_add_basket)
        buttonAddBasket.setOnClickListener {
            Baskets.baskets.add(Baskets.baskets.size - 1, Basket())
            appleBasketAdapter.notifyItemInserted(Baskets.baskets.size - 1)
        }

        /**
         * По нажатию кнопки удаляем все View "Корзина" и "Яблоко".
         * */
        val buttonRemoveAllBaskets: Button = findViewById(R.id.button_remove_all_baskets)
        buttonRemoveAllBaskets.setOnClickListener {
            Baskets.baskets.removeAll { e -> e is Basket || e is Apple }
            appleBasketAdapter.notifyItemRangeRemoved(0, Baskets.baskets.size - 1)
            val summary: Summary = Baskets.baskets[Baskets.baskets.size - 1] as Summary
            summary.countOfApples = Baskets.baskets.size - 1
            appleBasketAdapter.notifyItemChanged(Baskets.baskets.size - 1)
        }

    }

    /**
     * Описываем логику работы со свайпами и перетаскиваниями в этом методе.
     * */
    override fun onResume() {
        super.onResume()

        val mIth = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            /**
             * Запрещаем перетаскивание элементов View "Корзина" и View "Сумма",
             * оставляя возможность только для View "Яблоко".
             * */
            override fun getDragDirs(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
                when (viewHolder) {
                    is BasketHolder -> return 0
                    is SummaryHolder -> return 0
                }
                return super.getDragDirs(recyclerView, viewHolder)
            }

            /**
             * Задаём логику перетаскивания элемента View "Яблоко", ограничивая
             * возможность его перемещения дальше View "Сумма".
             * */
            @SuppressLint("NotifyDataSetChanged")
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder, target: ViewHolder): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                if (toPos <= Baskets.baskets.size - 2) {
                    selectActionInsert(
                        fromPos,
                        toPos,
                        countApples(target, fromPos)
                    )
                }
                appleBasketAdapter.notifyItemMoved(fromPos, toPos)
                return true
            }

            /**
             * Задаём возможные направления свайпа для каждого типа View.
             * */
            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
                when (viewHolder) {
                    is BasketHolder -> return ItemTouchHelper.LEFT
                    is AppleHolder -> return ItemTouchHelper.RIGHT
                    is SummaryHolder -> return 0
                }
                return super.getSwipeDirs(recyclerView, viewHolder)
            }

            /**
             * Задаём логику свайпа элементов View "Яблоко" и View "Корзина".
             * */
            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val view: View = viewHolder.itemView
                val imageView = view.findViewById<ImageView>(R.id.apple_view)
                when (Baskets.baskets[viewHolder.adapterPosition]) {
                    is Apple -> {
                        Baskets.baskets.removeAt(viewHolder.adapterPosition)
                        undoAppleAnimationEffect(imageView)
                        appleBasketAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                        countDeletedElements()
                        appleBasketAdapter.notifyItemChanged(Baskets.baskets.size - 1)
                    }
                    is Basket -> {   //Если свайпаемый элемент "Корзина"
                        val index = viewHolder.adapterPosition     //Получаем индекс её позиции в списке RecyclerView
                        Baskets.baskets.removeAt(index)   //Удаляем "Корзину"
                        appleBasketAdapter.notifyItemRemoved(viewHolder.adapterPosition)   //Оповещаем адаптер об удалении "Корзины"
                        while (Baskets.baskets[index] is Apple) {    //Пока следующий элемент после корзины "Яблоко"
                            val holder = recyclerView.findViewHolderForAdapterPosition(index)   //Запрашиваем холдер на это "Яблоко"
                            val appleView: View = holder?.itemView!!   //Получаем его View
                            val imageApple = appleView.findViewById<ImageView>(R.id.apple_view)    //Получаем дочернее View картинки
                            undoAppleAnimationEffect(imageApple)   //Отменяем у него эффект анимации
                            Baskets.baskets.removeAt(index)    //Удаляем это "Яблоко"
                            appleBasketAdapter.notifyItemRemoved(index)   //Оповещаем адаптер об удалении "Яблока"
                            countDeletedElements()   //Подсчитываем количество "Яблок" и передаем в итоговое поле "Сумма"
                            appleBasketAdapter.notifyItemChanged(Baskets.baskets.size - 1)
                        }
                    }
                }
            }
        })
        mIth.attachToRecyclerView(recyclerView)
    }

    /**
     * Подсчитываем количество элементов View "Яблоко" в списке и передаём
     * его во View "Сумма".
     * */
    fun countDeletedElements() {
        val summary: Summary = Baskets.baskets[Baskets.baskets.size - 1] as Summary
        summary.countOfApples--
    }

    /**
     * Кладём во View "Корзина" элемент View "Яблоко" или не кладём в
     * зависимости от их текущего количества.
     * */
    private fun selectActionInsert(fromPos: Int, toPos: Int, quantityOfApples: Int) {
        if (quantityOfApples < 3) {
            val apple: Apple = Baskets.baskets.removeAt(fromPos) as Apple
            Baskets.baskets.add(toPos, apple)
            findMovedImageView(fromPos)
        } else {
            makeToastMessage(this, R.string.unlimit_moving_message)
        }
    }

    /**
     * Находим адаптер перемещённого по списку элемента View "Яблоко" и
     * задаём ему анимированные свойства.
     * */
    private fun findMovedImageView(fromPos: Int) {
        val holder = recyclerView.findViewHolderForAdapterPosition(fromPos)
        val view: View = holder?.itemView!!
        val imageView = view.findViewById<ImageView>(R.id.apple_view)
        makeAppleAnimation(imageView)
    }

    /**
     * Подсчитываем количество элементов View "Яблоко", принадлежащих элементу
     * View "Корзина", в которую собираемся переместить "Яблоко". Подсчёт
     * производится в зависимости от движения по списку по направлениям:
     * 1) от исходной позиции к началу списка;
     * 2) от исходной позиции к концу списка
     * */
    private fun countApples(target: ViewHolder, fromPos: Int): Int {
        var quantityOfApples = 0
        if (target.adapterPosition < fromPos) {
            for (i in target.adapterPosition - 1 downTo 0) {
                if (Baskets.baskets[i] is Apple) quantityOfApples++
                if (Baskets.baskets[i] is Basket) break
            }
        } else {
            for (i in target.adapterPosition + 1 until Baskets.baskets.size) {
                if (Baskets.baskets[i] is Apple) quantityOfApples++
                if (Baskets.baskets[i] is Basket) break
            }
        }
        return quantityOfApples
    }

}