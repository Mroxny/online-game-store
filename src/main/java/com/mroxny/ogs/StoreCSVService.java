package com.mroxny.ogs;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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
    private String getLineByColumn(String path, int column,String value){
        List<String> lines = readCSV(path);
        return getLineByColumn(lines,column,value);
    }
    private String getLineByColumn(List<String> lines, int column,String value){
        for(String s : lines){
            String[] vals = s.split(",");
            if(vals[column].equalsIgnoreCase(value)){
                return s;
            }
        }
        return null;
    }
    private int getIndexByColumn(List<String> lines, int column,String value){
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

    private int getNewId(List<String> lines){
        int max = lines
                .stream()
                .mapToInt(v -> Integer.parseInt(v.split(",")[0]))
                .max().orElseThrow(NoSuchElementException::new);

        return ++max;
    }


    @Override
    public ResponseDTO getAllGames(String order) {
        List<String> lines = readCSV(FILE_GAMES);
        List<Game> games = makeGamesFromCSV(lines, order);


        return new ResponseDTO(HttpStatus.OK,"OK", games);
    }

    @Override
    public ResponseDTO getGameById(int id) {
        String line = getLineByColumn(FILE_GAMES, 0, id+"");

        if(line == null) return new ResponseDTO(HttpStatus.BAD_REQUEST, "No game with that id", null);

        Game g = makeGameFromCSV(line);
        return new ResponseDTO(HttpStatus.OK, "OK", Collections.singletonList(g));
    }

    //TODO: Make better search engine
    @Override
    public ResponseDTO getGamesByName(String name, String order){

        List<String> lines = getLinesByColumn(FILE_GAMES, 1, name);
        if(lines.size() < 1) return new ResponseDTO(HttpStatus.NO_CONTENT, "There's no games with that name", null);

        List<Game> g = makeGamesFromCSV(lines, order);
        return new ResponseDTO(HttpStatus.OK, "OK", g);
    }

    @Override
    public ResponseDTO getGamesByGenre(String genre,String order) {
        String target = getLineByColumn(FILE_GENRES, 1, genre);
        if(target == null) return new ResponseDTO(HttpStatus.NOT_FOUND, "Cant find genre "+genre, null);

        int genreId = Integer.parseInt(target.split(",")[0]);
        List<String> gameStrings = getManyToMany(FILE_GAMES_GENRES, FILE_GAMES, genreId, 1,0);
        if(gameStrings.size() < 1) return new ResponseDTO(HttpStatus.NO_CONTENT, "No games with genre "+genre, null);

        List<Game> g = makeGamesFromCSV(gameStrings, order);
        return new ResponseDTO(HttpStatus.OK, "OK", g);
    }

    @Override
    public ResponseDTO insertGame(GameDTO game) {
        List<String> games = readCSV(FILE_GAMES);
        int newGameId = getNewId(games);
        String gameLine = newGameId+","+game;
        writeInCSV(FILE_GAMES, gameLine, true);

        return new ResponseDTO(HttpStatus.OK, "OK", null);
    }

    @Override
    public ResponseDTO updateGame(int id, GameDTO game) {
        List<String> lines = readCSV(FILE_GAMES);
        int index = getIndexByColumn(lines, 0, id+"");
        String gameLine = id+","+game;
        lines.add(index, gameLine);

        for(String s: lines){
            writeInCSV(FILE_GAMES, s, false);

        }

        return new ResponseDTO(HttpStatus.OK, "OK", null);

    }

    @Override
    public ResponseDTO deleteGame(int id) {
        List<String> lines = readCSV(FILE_GAMES);
        int index = getIndexByColumn(lines, 0, id+"");
        lines.remove(index);

        for(String s: lines){
            writeInCSV(FILE_GAMES, s, false);
        }

        return new ResponseDTO(HttpStatus.OK, "OK", null);
    }

}
