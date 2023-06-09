package com.mroxny.ogs;

import lombok.Data;

import java.util.List;

@Data
public class Game {
    private int id;
    private String name;
    private String cover;
    private String smallDesc;
    private String bigDesc;
    private String premiere;
    private float price;
    private List<String> trailers;
    private List<String> photos;
    private List<String> genres;
    private List<String> tags;
    private Studio studio;
    private Requirements requirements;

}
