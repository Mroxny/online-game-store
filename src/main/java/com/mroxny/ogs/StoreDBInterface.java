package com.mroxny.ogs;

import java.util.List;

public interface StoreDBInterface {
    public ResultDTO<List<Game>> getAllGames(String order);

    public ResultDTO<Game> getGameById(int id);

    public ResultDTO<List<Game>> getGamesByName(String name, String order);

    public ResultDTO<List<Game>> getGamesByGenre(String genre, String order);

    public ResultDTO<String> insertGame(GameDTO game);

    public ResultDTO<String> insertStudio(StudioDTO studio);

    public ResultDTO<String> insertRequirements(RequirementsDTO requirements);

    public ResultDTO<String> updateGame(int id, GameDTO game);

    public ResultDTO<String> deleteGame(int id);

}
