package com.mroxny.ogs;

import java.util.List;

public interface StoreDBInterface {
    public List<Game> getAllGames();

    public Game getGameById(int id);

}
