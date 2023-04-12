package com.mroxny.ogs;

import org.springframework.http.HttpStatus;
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


    @Override
    public ResultDTO<List<Game>> getAllGames(String order) {
        List<String> lines = readCSV(FILE_GAMES);
        List<Game> games = makeGamesFromCSV(lines, order);


        return new ResultDTO<>(HttpStatus.OK,"OK", games);
    }

    @Override
    public ResultDTO<Game> getGameById(int id) {
        String line = getLineByColumn(FILE_GAMES, 0, id+"");

        if(line == null) return new ResultDTO<>(HttpStatus.BAD_REQUEST, "No game with that id", null);

        Game g = makeGameFromCSV(line);
        return new ResultDTO<>(HttpStatus.OK, "OK", g);
    }

    //TODO: Make better search engine
    @Override
    public ResultDTO<List<Game>> getGamesByName(String name, String order){

        List<String> lines = getLinesByColumn(FILE_GAMES, 1, name);
        if(lines.size() < 1) return new ResultDTO<>(HttpStatus.NO_CONTENT, "There's no games with that name", null);

        List<Game> g = makeGamesFromCSV(lines, order);
        return new ResultDTO<>(HttpStatus.OK, "OK", g);
    }

    @Override
    public ResultDTO<List<Game>> getGamesByGenre(String genre, String order) {
        String target = getLineByColumn(FILE_GENRES, 1, genre);
        if(target == null) return new ResultDTO<>(HttpStatus.NOT_FOUND, "Cant find genre "+genre, null);

        int genreId = Integer.parseInt(target.split(",")[0]);
        List<String> gameStrings = getManyToMany(FILE_GAMES_GENRES, FILE_GAMES, genreId, 1,0);
        if(gameStrings.size() < 1) return new ResultDTO<>(HttpStatus.NO_CONTENT, "No games with genre "+genre, null);

        List<Game> g = makeGamesFromCSV(gameStrings, order);
        return new ResultDTO<>(HttpStatus.OK, "OK", g);
    }

    @Override
    public ResultDTO<String> insertGame(GameDTO game) {
        List<String> games = readCSV(FILE_GAMES);
        int newGameId = getNewId(games);
        String gameLine = newGameId+","+game;
        writeInCSV(FILE_GAMES, gameLine, true);

        return new ResultDTO<>(HttpStatus.OK, "OK","Id: "+newGameId);
    }

    @Override
    public ResultDTO<String> insertStudio(StudioDTO studio) {
        List<String> studios = readCSV(FILE_STUDIOS);
        int newStudioId = getNewId(studios);
        String studioLine = newStudioId+","+studio;
        writeInCSV(FILE_STUDIOS, studioLine, true);

        return new ResultDTO<>(HttpStatus.OK, "OK","Id: "+newStudioId);
    }

    @Override
    public ResultDTO<String> insertRequirements(RequirementsDTO requirements) {
        List<String> reqs = readCSV(FILE_REQUIREMENTS);
        int newReqsId = getNewId(reqs);
        String reqsLine = newReqsId+","+requirements;
        writeInCSV(FILE_REQUIREMENTS, reqsLine, true);

        return new ResultDTO<>(HttpStatus.OK, "OK","Id: "+newReqsId);
    }


    @Override
    public ResultDTO<String> updateGame(int id, GameDTO game) {
        List<String> lines = readCSV(FILE_GAMES);
        int index = getIndexByColumn(lines, 0, id+"");
        if(index == -1) return new ResultDTO<>(HttpStatus.NOT_FOUND, "Not found game", "Cant find game with id"+id);

        String gameLine = id+","+game;
        lines.remove(index);
        lines.add(index, gameLine);
        writeInCSV(FILE_GAMES, lines.get(0), false);

        for(int i = 1; i < lines.size(); i++){
            writeInCSV(FILE_GAMES, lines.get(i), true);
        }

        return new ResultDTO<>(HttpStatus.OK, "OK", "Updated game with id "+id);

    }

    @Override
    public ResultDTO<String> deleteGame(int id) {
        List<String> lines = readCSV(FILE_GAMES);
        int index = getIndexByColumn(lines, 0, id+"");
        if(index == -1) return new ResultDTO<>(HttpStatus.NOT_FOUND, "Cant find game with id"+id, null);

        lines.remove(index);
        writeInCSV(FILE_GAMES, lines.get(0), false);

        for(int i = 1; i < lines.size(); i++){
            writeInCSV(FILE_GAMES, lines.get(i), true);
        }

        return new ResultDTO<>(HttpStatus.OK, "OK", "Deleted game with id "+id);
    }


    /**
     * Loads a CSV file and separates it into lines
     * @param path path to the file
     * @return a List of String with the loaded lines
     */
    public List<String> readCSV(String path){
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

    /**
     * Depending on the option, it adds or overwrites a line to the CSV file
     * @param path path to the file
     * @param line String that will be added
     * @param append specifies whether the line is to be added or to replace the content of the entire file
     */
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

    /**
     * Creates a Game object from the given line
     * @return a newly created Game object
     */
    private Game makeGameFromCSV(String line){
        String[] values = line.split(",");
        Game game = new Game();

        try{
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

        }
        catch (Exception ex){
            ex.printStackTrace();
        }


        return game;
    }

    /**
     * Creates a list of the Game object and sorts it
     * @param lines source needed for iteration
     * @param order the value by which it sorts the list
     * @return a sorted list of the Game object
     */
    private List<Game> makeGamesFromCSV(List<String> lines, String order){
        List<Game> games = new ArrayList<>();
        for(String s : lines){
            games.add(makeGameFromCSV(s));
        }

        GameComparator gc = new GameComparator(order);
        games.sort(gc);

        return games;
    }

    /**
     * Retrieves values from target table via associative table
     * @param path1 path to the associative table
     * @param path2 path to the target table
     * @param id source id
     * @param searchColumn alternate column of an associative table
     * @param targetColumn search column of the target table
     * @return a List of String with the found values from the target table
     */
    private List<String> getManyToMany(String path1, String path2, int id, int searchColumn, int targetColumn){
        List<String> lines1 = readCSV(path1);
        List<String> lines2 = readCSV(path2);
        List<String> res = new ArrayList<>();

        for(String s1 : lines1){
            String[] vals1 = s1.split(",");
            int idFromFile1 = Integer.parseInt(vals1[searchColumn]);
            if(idFromFile1 == id){
                int target = Integer.parseInt(vals1[targetColumn]);
                for(String s2 : lines2){
                    String[] vals2 = s2.split(",");
                    int idFromFile2 = Integer.parseInt(vals2[0]);
                    if(idFromFile2 == target)res.add(s2);

                }
            }
        }
        return res;
    }

    /**
     * Retrieves table values from a one-to-many table
     * @param path path to the target table
     * @param id source id
     * @return a List of String with the found values from the target table
     */
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

    /**
     * Checks which lines match the given column with the given value from a table
     * @param path path to the target table
     * @param column column index starting at 0
     * @param value check value
     * @return a String List of lines that match a condition
     */
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

    /**
     * Checks which line matches the given column with the given value from a table
     * @param lines lines of the target table
     * @param column column index starting at 0
     * @param value check value
     * @return a String line that match a condition
     */
    private String getLineByColumn(List<String> lines, int column,String value){
        for(String s : lines){
            String[] vals = s.split(",");
            if(vals[column].equalsIgnoreCase(value)){
                return s;
            }
        }
        return null;
    }

    /**
     * Checks which line meets the given condition
     * @param lines source lines
     * @param column column index starting at 0
     * @param value check value
     * @return index number from the given list that satisfies the condition
     */
    private int getIndexByColumn(List<String> lines, int column,String value){
        for(int i = 0; i<lines.size(); i++){
            String[] vals = lines.get(i).split(",");
            if(vals[column].equalsIgnoreCase(value)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Retrieves values from the specified column only
     * @param list source lines
     * @param column column index starting at 0
     * @return a List of String that contains only the values in the given column
     */
    private List<String> getValuesFromLines(List<String> list, int column){
        List<String> res = new ArrayList<>();
        for(String s:list){
            String[] vals = s.split(",");
            res.add(vals[column]);
        }
        return res;
    }

    /**
     * Searches for a new id from a given table
     * @param lines source lines where first column is id
     * @return a number with a new id
     */
    private int getNewId(List<String> lines){
        int max = lines
                .stream()
                .mapToInt(v -> Integer.parseInt(v.split(",")[0]))
                .max().orElseThrow(NoSuchElementException::new);

        return ++max;
    }
}
