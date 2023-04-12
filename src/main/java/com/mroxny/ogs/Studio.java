package com.mroxny.ogs;

import java.util.List;

public class Studio {
    private int id;
    private String name;
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Makes new Studio object based on the given line from CSV file
     * @param line line from source CSV file
     * @return new Studio object
     */
    public static Studio getFromCSV(String line){
        String[] vals = line.split(",");
        Studio studio = new Studio();

        studio.setId(Integer.parseInt(vals[0]));
        studio.setName(vals[1]);
        studio.setCountry(vals[2]);

        return studio;
    }
}
