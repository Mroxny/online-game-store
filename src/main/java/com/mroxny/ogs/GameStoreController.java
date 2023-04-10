package com.mroxny.ogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class GameStoreController {

    private StoreDBInterface dbManager;

    @Autowired
    public GameStoreController(StoreDBInterface storeDBInterface){
        this.dbManager = storeDBInterface;
    }

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames(){
        List<Game> games = dbManager.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int id){
        Game games = new Game();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/games/{genre}")
    public ResponseEntity<Game> getGameByGenre(@PathVariable String genre){
        Game games = new Game();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/findGame")
    public ResponseEntity<List<Game>> getAllGames(@RequestParam String query){
        List<Game> games = dbManager.getAllGames();
        return ResponseEntity.ok(games);
    }
}
