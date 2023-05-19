package com.mroxny.ogs;

import lombok.Data;

import java.util.List;

@Data
public class Studio {
    private int id;
    private String name;
    private String country;

    /**
     * Makes new Studio object based on the given line from CSV file
     * @param line line from source CSV file
     * @return new Studio object
     */
    public static Studio getFromCSV(String line){
        String[] vals = line.split(",");
        Studio studio = new Studio();
        try{
            studio.setId(Integer.parseInt(vals[0]));
            studio.setName(vals[1]);
            studio.setCountry(vals[2]);
        }
        catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }


        return studio;
    }
}
