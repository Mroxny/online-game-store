package com.mroxny.ogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<Game>> getAllGames(@RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        List<Game> games = dbManager.getAllGames(order);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int id){
        Game game = dbManager.getGameById(id);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/games/{genre}")
    public ResponseEntity<List<Game>> getGamesByGenre(@PathVariable String genre, @RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        List<Game> games = dbManager.getGamesByGenre(genre, order);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/findGame")
    public ResponseEntity<List<Game>> findGames(@RequestParam String query){
        List<Game> games = new ArrayList<>();
        return ResponseEntity.ok(games);
    }

    @PostMapping("/insertGame")
    public ResponseEntity<Game> insertGame(@RequestParam GameDTO game){
        Game g = new Game();
        return ResponseEntity.ok(g);
    }

    @PutMapping("/updateGame/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable int id, @RequestParam GameDTO game){
        Game g = new Game();
        return ResponseEntity.ok(g);
    }

    @DeleteMapping("/deleteGame/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable int id){
        Game g = new Game();
        return ResponseEntity.ok(g);
    }
}
