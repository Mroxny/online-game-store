package com.mroxny.ogs;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreCSVService implements StoreDBInterface{
    private static final String FILE_GAMES="./csvDB/games.csv";
    private static final String FILE_TRAILERS="./csvDB/trailers.csv";
    private static final String FILE_IMAGES="./csvDB/images.csv";
    private static final String FILE_STUDIOS="./csvDB/studios.csv";
    private static final String FILE_REQUIREMENTS="./csvDB/requirements.csv";
    private static final String FILE_GENRES="./csvDB/genres.csv";
    private static final String FILE_TAGS="./csvDB/tags.csv";
    private static final String FILE_GAMES_GENRES="./csvDB/games_genres.csv";
    private static final String FILE_GAMES_TAGS="./csvDB/games_tags.csv";




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

    private void writeInCSV(String path, String line, boolean append){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, append));
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        game.setTrailers(getValuesFromLines(getOneToMany(FILE_TRAILERS, game.getId()), 1));
        game.setPhotos(getValuesFromLines(getOneToMany(FILE_IMAGES, game.getId()), 1));

        game.setStudio(Studio.getFromCSV(getLinesByColumn(FILE_STUDIOS, 0, values[7]).get(0)));
        game.setRequirements(Requirements.getFromCSV(getLinesByColumn(FILE_REQUIREMENTS, 0, values[8]).get(0)));

        game.setGenres(getValuesFromLines(getManyToMany(FILE_GAMES_GENRES,FILE_GENRES, game.getId(),0,1),1));
        game.setTags(getValuesFromLines(getManyToMany(FILE_GAMES_TAGS,FILE_TAGS, game.getId(),0,1),1));


        return game;
    }

    private List<Game> makeGamesFromCSV(List<String> lines, String order){
        List<Game> games = new ArrayList<>();
        for(String s : lines){
            games.add(makeGameFromCSV(s));
        }

        GameComparator gc = new GameComparator(order);
        games.sort(gc);

        return games;
    }

//    private String

    private List<String> getManyToMany(String path1, String path2, int id, int searchIndex, int targetIndex){
        List<String> lines1 = readCSV(path1);
        List<String> lines2 = readCSV(path2);
        List<String> res = new ArrayList<>();

        for(String s1 : lines1){
            String[] vals1 = s1.split(",");
            int idFromFile1 = Integer.parseInt(vals1[searchIndex]);
            if(idFromFile1 == id){
                int target = Integer.parseInt(vals1[targetIndex]);
                for(String s2 : lines2){
                    String[] vals2 = s2.split(",");
                    int idFromFile2 = Integer.parseInt(vals2[0]);
                    if(idFromFile2 == target)res.add(s2);

                }
            }
        }
        return res;
    }

    private List<String> getOneToMany(String path, int id){
        List<String> lines = readCSV(path);
        List<String> res = new ArrayList<>();

        for(String s : lines){
            String[] vals = s.split(",");
            int idFromFile = Integer.parseInt(vals[vals.length-1]);
            if(idFromFile == id){
                res.add(s);
            }
        }
        return res;
    }

    private List<String> getLinesByColumn(String path, int column,String value){
        List<String> lines = readCSV(path);
        List<String> res = new ArrayList<>();
        for(String s : lines){
            String[] vals = s.split(",");
            if(vals[column].equalsIgnoreCase(value)){
                res.add(s);
            }
        }
        return res;
    }
    private int getIndexByColumn(String path, int column,String value){
        List<String> lines = readCSV(path);

        for(int i = 0; i<lines.size(); i++){
            String[] vals = lines.get(i).split(",");
            if(vals[column].equalsIgnoreCase(value)){
                return i;
            }
        }
        return -1;
    }

    private List<String> getValuesFromLines(List<String> list, int column){
        List<String> res = new ArrayList<>();
        for(String s:list){
            String[] vals = s.split(",");
            res.add(vals[column]);
        }
        return res;
    }

    private int getNewId(){
        //I'm so sorry about this :,)
        /*int min = 0;
        for(String s : lines){
            String[] vals = s.split(",");
            int id = Integer.parseInt(vals[0]);
            if(id > min) min = id;
        }
        return ++min;*/
        return 0;
    }




    @Override
    public List<Game> getAllGames(String order) {
        List<String> lines = readCSV(FILE_GAMES);
        return makeGamesFromCSV(lines, order);
    }

    @Override
    public Game getGameById(int id) {
        String line = getLinesByColumn(FILE_GAMES, 0, id+"").get(0);
        return makeGameFromCSV(line);
    }

    //TODO: Make better search engine
    @Override
    public List<Game> getGamesByName(String name, String order){

        List<String> lines = getLinesByColumn(FILE_GAMES, 1, name);
        return makeGamesFromCSV(lines, order);
    }

    @Override
    public List<Game> getGamesByGenre(String genre,String order) {
        String genreId = getLinesByColumn(FILE_GENRES, 1, genre).get(0).split(",")[0];
        List<String> gameStrings = getManyToMany(FILE_GAMES_GENRES, FILE_GAMES, Integer.parseInt(genreId), 1,0);

        return makeGamesFromCSV(gameStrings, order);
    }
}
