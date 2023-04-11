package com.mroxny.ogs;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreCSVService implements StoreDBInterface{

    private List<String> readCSV(){
        return new ArrayList<>();
    }

    private void appendToCSV(){

    }

    private void editCSV(){

    }
    
    @Override
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        games.add(new Game());
        games.add(new Game());

        return games;
    }
}
