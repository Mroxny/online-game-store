package com.mroxny.ogs;

import java.util.List;

public interface StoreDBInterface {
    public List<Game> getAllGames(String order);

    public Game getGameById(int id);

    public List<Game> getGamesByName(String name, String order);

    public List<Game> getGamesByGenre(String genre,String order);

}
