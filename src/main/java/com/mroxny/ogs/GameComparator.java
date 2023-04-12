package com.mroxny.ogs;

import java.util.Comparator;

public class GameComparator implements Comparator<Game> {

    private String orderValue;

    public GameComparator(String orderValue){
        this.orderValue = orderValue.toLowerCase();
    }

    /**
     * Compares two objects based on orderValue param given in class constructor
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     */
    @Override
    public int compare(Game o1, Game o2) {
        int res = 0;
        switch (orderValue){
            case "date" -> res = o1.getPremiere().compareTo(o2.getPremiere());
            case "name" -> res = o1.getName().compareTo(o2.getName());
            case "price" -> res = Float.compare(o1.getPrice(), o2.getPrice());
        }
        return res;
    }
}
