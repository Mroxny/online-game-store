package com.mroxny.ogs;

import java.util.Comparator;

public class GameComparator implements Comparator<Game> {

    private String orderValue;

    public GameComparator(String orderValue){
        this.orderValue = orderValue.toLowerCase();
    }

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
