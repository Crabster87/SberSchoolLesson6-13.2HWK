package crabster.rudakov.sberschoollesson6hwk

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Вспомогательный класс для управления анимацией и тост-сообщениями.
 * */
class InteractiveHelper {

    companion object {
        /**
         * Устанавливаем параметры анимации для перемещённых объектов типа "Яблоко".
         * */
        fun makeAppleAnimation(view: View) {
            val animator: ViewPropertyAnimator = view.animate()
            animator.scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.scaleX = 1.5f
                        view.scaleY = 1.5f
                        view.alpha = 0.5f
                    }
                })
        }

        /**
         * Отменяем параметры анимации для перемещённых объектов типа "Яблоко".
         * */
        fun undoAppleAnimationEffect(view: View) {
            val animator: ViewPropertyAnimator = view.animate()
            animator.scaleX(1.5f)
                .scaleY(1.5f)
                .alpha(0.9f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.scaleX = 1.0f
                        view.scaleY = 1.0f
                        view.alpha = 1.0f
                    }
                })
        }

        /**
         * Формируем тост-мессэдж.
         * */
        fun makeToastMessage(context: Context, @StringRes message: Int) {
            val toast = Toast.makeText(
                context,
                context.getString(message),
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }
    }

}