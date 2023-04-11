package com.mroxny.ogs;

import java.util.List;

public interface StoreDBInterface {
    public List<Game> getAllGames();

    public List<Game> getGameById(int id);

}
