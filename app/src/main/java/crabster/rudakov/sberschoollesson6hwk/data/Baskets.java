package crabster.rudakov.sberschoollesson6hwk.data;

import java.util.ArrayList;
import java.util.List;

import crabster.rudakov.sberschoollesson6hwk.Basket;
import crabster.rudakov.sberschoollesson6hwk.Elements;
import crabster.rudakov.sberschoollesson6hwk.Summary;

public class Baskets {

    public static List<Elements> baskets = new ArrayList<>();

    /**
     * Список стартует с 3 корзинами и итоговым полем.
     * */
    public static void addBaskets() {
        baskets.add(new Basket());
        baskets.add(new Basket());
        baskets.add(new Basket());
        baskets.add(new Summary(0));
    }

}
