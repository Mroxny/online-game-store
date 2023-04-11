package com.mroxny.ogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> getAllGames(@RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        ResponseDTO response = dbManager.getAllGames(order);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent());
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Object> getGameById(@PathVariable int id){
        ResponseDTO response = dbManager.getGameById(id);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent().get(0));
    }

    @GetMapping("/games/{genre}")
    public ResponseEntity<Object> getGamesByGenre(@PathVariable String genre, @RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        ResponseDTO response = dbManager.getGamesByGenre(genre, order);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent());
    }

    @GetMapping("/findGame")
    public ResponseEntity<Object> findGames(@RequestParam String query, @RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        ResponseDTO response = dbManager.getGamesByName(query, order);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent());
    }

    @PostMapping("/insertGame")
    public ResponseEntity<Object> insertGame(@RequestParam GameDTO game){
        Game g = new Game();
        return ResponseEntity.ok(g);
    }

    @PutMapping("/updateGame/{id}")
    public ResponseEntity<Object> updateGame(@PathVariable int id, @RequestParam GameDTO game){
        Game g = new Game();
        return ResponseEntity.ok(g);
    }

    @DeleteMapping("/deleteGame/{id}")
    public ResponseEntity<Object> deleteGame(@PathVariable int id){
        Game g = new Game();
        return ResponseEntity.ok(g);
    }
}
