package crabster.rudakov.sberschoollesson6hwk

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import java.lang.IndexOutOfBoundsException

/**
 * Вспомогательный класс-обёртка для борьбы с непредсказуемо появляющимся исключением
 * "ArrayOutOfBoundsException" при работе с RecyclerView.
 * */
class MyLinearLayoutManager : LinearLayoutManager {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout)

    constructor(context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        try {
            //The previous array out-of-bounds problem is captured here...
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

}