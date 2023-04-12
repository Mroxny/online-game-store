package com.mroxny.ogs;

import java.util.List;

public interface StoreDBInterface {
    public ResponseDTO getAllGames(String order);

    public ResponseDTO getGameById(int id);

    public ResponseDTO getGamesByName(String name, String order);

    public ResponseDTO getGamesByGenre(String genre,String order);

    public ResponseDTO insertGame(GameDTO game);

    public ResponseDTO insertStudio(StudioDTO studio);

    public ResponseDTO updateGame(int id, GameDTO game);

    public ResponseDTO deleteGame(int id);

}
