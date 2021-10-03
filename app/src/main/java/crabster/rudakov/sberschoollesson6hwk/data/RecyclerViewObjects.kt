package crabster.rudakov.sberschoollesson6hwk.data

/**
* Общий интерфейс для возможных View.
* */
interface Elements

/**
 * Корзины с одинаковой характеристикой.
 * */
class Basket : Elements {
    val basketName by lazy { "PUT ONE APPLE INTO BASKET" }
}

/**
 * Яблоко.
 * */
class Apple : Elements


/**
 * Итоговое поле с общим количеством яблок.
 * */
class Summary(var countOfApples: Int) : Elements