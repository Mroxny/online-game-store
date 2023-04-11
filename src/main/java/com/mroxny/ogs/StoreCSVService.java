package com.mroxny.ogs;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StoreCSVService implements StoreDBInterface{
    private static String FILE_GAMES="./csvDB/games.csv";
    private static String FILE_TRAILERS="./csvDB/trailers.csv";
    private static String FILE_IMAGES="./csvDB/images.csv";
    private static String FILE_STUDIOS="./csvDB/studios.csv";
    private static String FILE_REQUIREMENTS="./csvDB/requirements.csv";


    private List<String> lines;

    private List<String> readCSV(String path){
        List<String> values = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                values.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private void appendToCSV(String path){

    }

    private void editCSV(String path){

    }

    private Game makeGameFromCSV(String line){
        String[] values = line.split(",");
        Game game = new Game();

        game.setId(Integer.parseInt(values[0]));
        game.setName(values[1]);
        game.setCover(values[2]);
        game.setSmallDesc(values[3]);
        game.setBigDesc(values[4]);
        game.setPremiere(values[5]);
        game.setPrice(Float.parseFloat(values[6]));
        game.setTrailers(getOneToMany(FILE_GAMES, game.getId()));
        game.setPhotos(getOneToMany(FILE_IMAGES, game.getId()));
        game.setStudio(Studio.getFromCSV(getObjectLineById(FILE_STUDIOS, Integer.parseInt(values[7]))));
        game.setRequirements(Requirements.getFromCSV(getObjectLineById(FILE_REQUIREMENTS, Integer.parseInt(values[8]))));

        return game;
    }

    private List<String> getOneToMany(String path, int id){
        List<String> lines = readCSV(path);
        List<String> res = new ArrayList<>();

        for(String s : lines){
            String[] vals = s.split(",");
            int idFromFile = Integer.parseInt(vals[vals.length-1]);
            if(idFromFile == id){
                res.add(vals[1]);
            }
        }
        return res;
    }

    private String getObjectLineById(String path, int id){
        List<String> lines = readCSV(path);
        for(String s : lines){
            String[] vals = s.split(",");
            if(Integer.parseInt(vals[0]) == id){
                return s;
            }
        }
        return "";
    }

    private int getNewId(){
        //I'm so sorry about this :,)
        int min = 0;
        for(String s : lines){
            String[] vals = s.split(",");
            int id = Integer.parseInt(vals[0]);
            if(id > min) min = id;
        }
        return ++min;
    }

    @Override
    public List<Game> getAllGames() {
        List<String> lines = readCSV(FILE_GAMES);
        List<Game> games = new ArrayList<>();
        for(String s : lines){
            games.add(makeGameFromCSV(s));
        }


        return games;
    }
}
